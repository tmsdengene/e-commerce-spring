package com.gibesystems.ecommerce.payment;

import com.gibesystems.ecommerce.payment.dto.PaymentRequest;
import com.gibesystems.ecommerce.payment.dto.PaymentResponse;
import com.gibesystems.ecommerce.payment.service.PaymentServiceImpl;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.entity.Order;
import com.gibesystems.ecommerce.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private PaymentServiceImpl paymentService;

    private User user;
    private Order order;
    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id("1").build();
        order = Order.builder().id(10L).build();
        payment = Payment.builder()
                .id(100L)
                .user(user)
                .order(order)
                .amount(200.0)
                .method("MOCK")
                .status("SUCCESS")
                .paymentTime(LocalDateTime.now())
                .build();
    }

    @Test
    void testInitiatePayment_Mock() {
        PaymentRequest request = PaymentRequest.builder()
                .orderId(10L)
                .amount(200.0)
                .method("MOCK")
                .build();
        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        PaymentResponse response = paymentService.initiatePayment(user, request);
        assertNotNull(response);
        assertEquals(100L, response.getPaymentId());
        assertEquals("MOCK", response.getMethod());
        assertEquals("SUCCESS", response.getStatus());
    }

    @Test
    void testGetPaymentByOrder_Found() {
        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrder(order)).thenReturn(Optional.of(payment));
        PaymentResponse response = paymentService.getPaymentByOrder(10L);
        assertNotNull(response);
        assertEquals(100L, response.getPaymentId());
    }

    @Test
    void testGetPaymentByOrder_NotFound() {
        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrder(order)).thenReturn(Optional.empty());
        PaymentResponse response = paymentService.getPaymentByOrder(10L);
        assertNull(response);
    }

    @Test
    void testGetLatestPaymentForUser() {
        when(paymentRepository.findByUser(user)).thenReturn(Collections.singletonList(payment));
        PaymentResponse response = paymentService.getLatestPaymentForUser(user);
        assertNotNull(response);
        assertEquals(100L, response.getPaymentId());
    }
}
