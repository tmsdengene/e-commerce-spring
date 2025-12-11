package com.gibesystems.ecommerce.payment.service;

import com.gibesystems.ecommerce.payment.Payment;
import com.gibesystems.ecommerce.payment.PaymentRepository;
import com.gibesystems.ecommerce.payment.dto.PaymentRequest;
import com.gibesystems.ecommerce.payment.dto.PaymentResponse;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.entity.Order;
import com.gibesystems.ecommerce.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public PaymentResponse initiatePayment(User user, PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));
        Payment payment = Payment.builder()
                .user(user)
                .order(order)
                .amount(request.getAmount())
                .method(request.getMethod())
                .status("PENDING")
                .paymentTime(LocalDateTime.now())
                .build();
        Payment savedPayment = paymentRepository.save(payment);
        return toPaymentResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return null;
        Payment payment = paymentRepository.findByOrder(order).orElse(null);
        return payment != null ? toPaymentResponse(payment) : null;
    }

    @Override
    public PaymentResponse getLatestPaymentForUser(User user) {
        List<Payment> payments = paymentRepository.findByUser(user);
        if (payments.isEmpty()) return null;
        Payment latest = payments.stream().max((a, b) -> a.getPaymentTime().compareTo(b.getPaymentTime())).orElse(payments.get(0));
        return toPaymentResponse(latest);
    }

    private PaymentResponse toPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .paymentTime(payment.getPaymentTime())
                .build();
    }
}
