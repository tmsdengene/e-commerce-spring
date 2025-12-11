package com.gibesystems.ecommerce.cart.service;

import com.gibesystems.ecommerce.cart.dto.*;
import com.gibesystems.ecommerce.cart.entity.Cart;
import com.gibesystems.ecommerce.cart.entity.CartItem;
import com.gibesystems.ecommerce.cart.repository.CartRepository;
import com.gibesystems.ecommerce.cart.repository.CartItemRepository;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.product.Product;
import com.gibesystems.ecommerce.product.ProductRepository;
import com.gibesystems.ecommerce.exception.ProductNotFoundException;
import com.gibesystems.ecommerce.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartDTO getCartByUser(User user) {
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> createCart(user));
        return toCartDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO addItemToCart(User user, AddCartItemRequest request) {
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> createCart(user));
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new ValidationException("Insufficient stock");
        }
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            cart.getItems().add(item);
            cartItemRepository.save(item);
        }
        cartRepository.save(cart);
        return toCartDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO updateCartItem(User user, UpdateCartItemRequest request) {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ValidationException("Cart not found"));
        CartItem item = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new ValidationException("Cart item not found"));
        if (item.getCart().getId().equals(cart.getId())) {
            if (item.getProduct().getStockQuantity() < request.getQuantity()) {
                throw new ValidationException("Insufficient stock");
            }
            item.setQuantity(request.getQuantity());
            cartItemRepository.save(item);
        }
        return toCartDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO removeCartItem(User user, RemoveCartItemRequest request) {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ValidationException("Cart not found"));
        CartItem item = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new ValidationException("Cart item not found"));
        if (item.getCart().getId().equals(cart.getId())) {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        }
        return toCartDTO(cart);
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ValidationException("Cart not found"));
        for (CartItem item : new ArrayList<>(cart.getItems())) {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        }
        cartRepository.save(cart);
    }

    private Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
        return cartRepository.save(cart);
    }

    private CartDTO toCartDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        // CartDTO expects Long userId, but User.id is String. You may want to update CartDTO to String userId, but for now, set as null or handle conversion if possible.
        dto.setUserId(null); // Or update CartDTO to String userId and set cart.getUser().getId()
        List<CartItemDTO> itemDTOs = new ArrayList<>();
        double total = 0;
        for (CartItem item : cart.getItems()) {
            CartItemDTO itemDTO = new CartItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getProduct().getPrice().doubleValue());
            itemDTO.setLowStockWarning(item.getProduct().getStockQuantity() <= 5);
            total += item.getProduct().getPrice().doubleValue() * item.getQuantity();
            itemDTOs.add(itemDTO);
        }
        dto.setItems(itemDTOs);
        dto.setTotalPrice(total);
        dto.setTax(total * 0.1); // Example: 10% tax
        dto.setDiscount(0); // Placeholder for discount logic
        return dto;
    }
}
