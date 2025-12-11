package com.gibesystems.ecommerce.checkout.controller;

import com.gibesystems.ecommerce.checkout.dto.CheckoutDTO;
import com.gibesystems.ecommerce.checkout.service.CheckoutService;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.entity.Order;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/{orderId}")
    public ResponseEntity<CheckoutDTO> checkout(@PathVariable Long orderId,
                                                @RequestParam String paymentMethod,
                                                Authentication authentication) {
        // Get logged-in user from authentication principal
        User user = (User) authentication.getPrincipal();
        Order order = new Order();
        order.setId(orderId);
        CheckoutDTO dto = checkoutService.checkout(user, order, paymentMethod);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<CheckoutDTO> getCheckoutByOrder(@PathVariable Long orderId) {
        Order order = new Order();
        order.setId(orderId);
        CheckoutDTO dto = checkoutService.getCheckoutByOrder(order);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<CheckoutDTO> getLatestCheckoutForUser(@PathVariable String userId) {
        User user = new User();
        user.setId(userId);
        CheckoutDTO dto = checkoutService.getLatestCheckoutForUser(user);
        return ResponseEntity.ok(dto);
    }
}
