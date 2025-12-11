package com.gibesystems.ecommerce.payment;

import com.gibesystems.ecommerce.payment.dto.PaymentRequest;
import com.gibesystems.ecommerce.payment.dto.PaymentResponse;
import com.gibesystems.ecommerce.payment.service.PaymentService;
import com.gibesystems.ecommerce.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponse> initiatePayment(@RequestBody PaymentRequest request,
                                                          Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        PaymentResponse response = paymentService.initiatePayment(user, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrder(@PathVariable Long orderId) {
        PaymentResponse response = paymentService.getPaymentByOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/latest")
    public ResponseEntity<PaymentResponse> getLatestPaymentForUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        PaymentResponse response = paymentService.getLatestPaymentForUser(user);
        return ResponseEntity.ok(response);
    }
}
