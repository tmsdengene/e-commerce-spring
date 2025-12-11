package com.gibesystems.ecommerce.order;

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
import com.gibesystems.ecommerce.order.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User user;
    private Product product;
    private Cart cart;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId("user-1");
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setStockQuantity(10);
        cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cart.getItems().add(cartItem);
    }

    @Test
    void testCreateOrder_Success() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCartItemIds(List.of(1L));
        request.setShippingAddress("123 Main St");
        request.setPaymentMethod("COD");
        Order order = new Order();
        order.setId(1L);
        order.setOrderUuid(UUID.randomUUID().toString());
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(200);
        order.setItems(new ArrayList<>());
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderDTO result = orderService.createOrder(user, request);
        assertEquals(1L, result.getId());
        assertEquals(200, result.getTotalAmount());
    }

    @Test
    void testGetOrderByUuid_Success() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderUuid("order-uuid");
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(200);
        order.setItems(new ArrayList<>());
        when(orderRepository.findByOrderUuid("order-uuid")).thenReturn(Optional.of(order));
        OrderDTO result = orderService.getOrderByUuid("order-uuid");
        assertEquals("order-uuid", result.getOrderUuid());
    }

    @Test
    void testUpdateOrderStatus_Success() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderUuid("order-uuid");
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(200);
        order.setItems(new ArrayList<>());
        when(orderRepository.findByOrderUuid("order-uuid")).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderDTO result = orderService.updateOrderStatus("order-uuid", "SHIPPED");
        assertEquals(OrderStatus.SHIPPED, result.getStatus());
    }

    @Test
    void testCancelOrder_Success() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderUuid("order-uuid");
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(200);
        order.setItems(new ArrayList<>());
        when(orderRepository.findByOrderUuid("order-uuid")).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        orderService.cancelOrder("order-uuid");
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void testGetOrderHistory_Success() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderUuid("order-uuid");
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(200);
        order.setItems(new ArrayList<>());
        when(orderRepository.findByUser(user)).thenReturn(List.of(order));
        OrderDTO result = orderService.getOrderHistory(user);
        assertEquals("order-uuid", result.getOrderUuid());
    }
}
