package com.gibesystems.ecommerce.checkout;

import com.gibesystems.ecommerce.checkout.entity.Checkout;
import com.gibesystems.ecommerce.checkout.repository.CheckoutRepository;
import com.gibesystems.ecommerce.checkout.service.CheckoutServiceImpl;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckoutServiceImplTest {
    @Mock
    private CheckoutRepository checkoutRepository;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private User user;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId("2f1c4c0d-8f0b-4cc0-8f8a-9d5e3b9de3ea");
        order = new Order();
        order.setId(10L);
    }

    @Test
    void testCheckout_Success() {
        Checkout checkout = new Checkout();
        checkout.setId(100L);
        checkout.setUser(user);
        checkout.setOrder(order);
        checkout.setCheckoutTime(LocalDateTime.now());
        checkout.setPaymentMethod("CARD");
        checkout.setPaymentConfirmed(false);
        when(checkoutRepository.save(any(Checkout.class))).thenReturn(checkout);
        var dto = checkoutService.checkout(user, order, "CARD");
        assertNotNull(dto);
        assertEquals(100L, dto.getId());
        assertEquals(1L, dto.getUserId());
        assertEquals(10L, dto.getOrderId());
        assertEquals("CARD", dto.getPaymentMethod());
        assertFalse(dto.isPaymentConfirmed());
    }

    @Test
    void testGetCheckoutByOrder_Found() {
        Checkout checkout = new Checkout();
        checkout.setId(101L);
        checkout.setUser(user);
        checkout.setOrder(order);
        checkout.setCheckoutTime(LocalDateTime.now());
        checkout.setPaymentMethod("PAYPAL");
        checkout.setPaymentConfirmed(true);
        when(checkoutRepository.findByOrder(order)).thenReturn(Optional.of(checkout));
        var dto = checkoutService.getCheckoutByOrder(order);
        assertNotNull(dto);
        assertEquals(101L, dto.getId());
        assertEquals("PAYPAL", dto.getPaymentMethod());
        assertTrue(dto.isPaymentConfirmed());
    }

    @Test
    void testGetCheckoutByOrder_NotFound() {
        when(checkoutRepository.findByOrder(order)).thenReturn(Optional.empty());
        var dto = checkoutService.getCheckoutByOrder(order);
        assertNull(dto);
    }

    @Test
    void testGetLatestCheckoutForUser() {
        Checkout checkout = new Checkout();
        checkout.setId(102L);
        checkout.setUser(user);
        checkout.setOrder(order);
        checkout.setCheckoutTime(LocalDateTime.now());
        checkout.setPaymentMethod("CASH");
        checkout.setPaymentConfirmed(true);
        when(checkoutRepository.findByUser(user)).thenReturn(Collections.singletonList(checkout));
        var dto = checkoutService.getLatestCheckoutForUser(user);
        assertNotNull(dto);
        assertEquals(102L, dto.getId());
        assertEquals("CASH", dto.getPaymentMethod());
    }
}
