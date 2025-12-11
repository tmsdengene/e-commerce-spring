package com.gibesystems.ecommerce.order.service;

import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.cart.entity.Cart;
import com.gibesystems.ecommerce.cart.entity.CartItem;
import com.gibesystems.ecommerce.cart.repository.CartRepository;
import com.gibesystems.ecommerce.order.dto.*;
import com.gibesystems.ecommerce.order.entity.Order;
import com.gibesystems.ecommerce.order.entity.OrderItem;
import com.gibesystems.ecommerce.order.entity.OrderStatus;
import com.gibesystems.ecommerce.order.repository.OrderRepository;
import com.gibesystems.ecommerce.order.repository.OrderItemRepository;
import com.gibesystems.ecommerce.product.Product;
import com.gibesystems.ecommerce.product.ProductRepository;
import com.gibesystems.ecommerce.exception.ProductNotFoundException;
import com.gibesystems.ecommerce.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public OrderDTO createOrder(User user, CreateOrderRequest request) {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ValidationException("Cart not found"));
        List<CartItem> cartItems = new ArrayList<>();
        for (Long cartItemId : request.getCartItemIds()) {
            Optional<CartItem> cartItemOpt = cart.getItems().stream().filter(i -> i.getId().equals(cartItemId)).findFirst();
            if (cartItemOpt.isEmpty()) throw new ValidationException("Cart item not found: " + cartItemId);
            cartItems.add(cartItemOpt.get());
        }
        Order order = new Order();
        order.setOrderUuid(UUID.randomUUID().toString());
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new ValidationException("Insufficient stock for product: " + product.getName());
            }
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice().doubleValue());
            orderItems.add(orderItem);
            total += orderItem.getPrice() * orderItem.getQuantity();
        }
        order.setItems(orderItems);
        order.setTotalAmount(total);
        // Save order and items in one go (cascade)
        Order savedOrder = orderRepository.save(order);
        // Remove ordered items from cart
        cart.getItems().removeAll(cartItems);
        cartRepository.save(cart);
        return toOrderDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderByUuid(String orderUuid) {
        Order order = orderRepository.findByOrderUuid(orderUuid).orElseThrow(() -> new ValidationException("Order not found"));
        return toOrderDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(String orderUuid, String status) {
        Order order = orderRepository.findByOrderUuid(orderUuid).orElseThrow(() -> new ValidationException("Order not found"));
        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
        return toOrderDTO(order);
    }

    @Override
    @Transactional
    public void cancelOrder(String orderUuid) {
        Order order = orderRepository.findByOrderUuid(orderUuid).orElseThrow(() -> new ValidationException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        // Optionally restock products
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }
    }

    @Override
    public OrderDTO getOrderHistory(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        // For simplicity, return the most recent order
        if (orders.isEmpty()) throw new ValidationException("No orders found");
        return toOrderDTO(orders.get(orders.size() - 1));
    }

    private OrderDTO toOrderDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderUuid(order.getOrderUuid());
        dto.setUserId(order.getUser().getId());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setTotalAmount(order.getTotalAmount());
        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            itemDTOs.add(itemDTO);
        }
        dto.setItems(itemDTOs);
        return dto;
    }
}
