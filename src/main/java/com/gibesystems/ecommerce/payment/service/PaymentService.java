package com.gibesystems.ecommerce.payment.service;

import com.gibesystems.ecommerce.payment.dto.PaymentRequest;
import com.gibesystems.ecommerce.payment.dto.PaymentResponse;
import com.gibesystems.ecommerce.auth.entity.User;

public interface PaymentService {
    PaymentResponse initiatePayment(User user, PaymentRequest request);
    PaymentResponse getPaymentByOrder(Long orderId);
    PaymentResponse getLatestPaymentForUser(User user);
}
