package com.gibesystems.ecommerce.cart;

import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.cart.dto.*;
import com.gibesystems.ecommerce.cart.entity.Cart;
import com.gibesystems.ecommerce.cart.entity.CartItem;
import com.gibesystems.ecommerce.cart.repository.CartRepository;
import com.gibesystems.ecommerce.cart.repository.CartItemRepository;
import com.gibesystems.ecommerce.cart.service.CartService;
import com.gibesystems.ecommerce.cart.service.CartServiceImpl;
import com.gibesystems.ecommerce.product.Product;
import com.gibesystems.ecommerce.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Product product;

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
    }

    @Test
    void testAddItemToCart_Success() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setQuantity(2);
        CartDTO result = cartService.addItemToCart(user, request);
        assertEquals(1, result.getItems().size());
        assertEquals(200, result.getTotalPrice());
    }

    @Test
    void testAddItemToCart_InsufficientStock() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setQuantity(20);
        assertThrows(Exception.class, () -> cartService.addItemToCart(user, request));
    }

    @Test
    void testGetCartByUser_CreatesNewCartIfNotExists() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        Cart cart = new Cart();
        cart.setId(2L);
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        CartDTO result = cartService.getCartByUser(user);
        assertEquals(2L, result.getId());
        assertTrue(result.getItems().isEmpty());
    }

    @Test
    void testUpdateCartItem_Success() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        CartItem item = new CartItem();
        item.setId(10L);
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(1);
        cart.setItems(new ArrayList<>());
        cart.getItems().add(item);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(10L)).thenReturn(Optional.of(item));
        UpdateCartItemRequest request = new UpdateCartItemRequest();
        request.setCartItemId(10L);
        request.setQuantity(5);
        CartDTO result = cartService.updateCartItem(user, request);
        assertEquals(5, result.getItems().get(0).getQuantity());
    }

    @Test
    void testRemoveCartItem_Success() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        CartItem item = new CartItem();
        item.setId(10L);
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(1);
        cart.setItems(new ArrayList<>());
        cart.getItems().add(item);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(10L)).thenReturn(Optional.of(item));
        RemoveCartItemRequest request = new RemoveCartItemRequest();
        request.setCartItemId(10L);
        CartDTO result = cartService.removeCartItem(user, request);
        assertTrue(result.getItems().isEmpty());
    }

    @Test
    void testClearCart_Success() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        CartItem item1 = new CartItem();
        item1.setId(10L);
        item1.setCart(cart);
        item1.setProduct(product);
        item1.setQuantity(1);
        CartItem item2 = new CartItem();
        item2.setId(11L);
        item2.setCart(cart);
        item2.setProduct(product);
        item2.setQuantity(2);
        cart.setItems(new ArrayList<>());
        cart.getItems().add(item1);
        cart.getItems().add(item2);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        cartService.clearCart(user);
        assertTrue(cart.getItems().isEmpty());
    }
}
