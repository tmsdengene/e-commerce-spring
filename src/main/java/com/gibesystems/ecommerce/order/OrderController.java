package com.gibesystems.ecommerce.order;

import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.dto.*;
import com.gibesystems.ecommerce.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(Authentication authentication,
            @RequestBody CreateOrderRequest request) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(orderService.createOrder(user, request));
    }

    @GetMapping("/{orderUuid}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable String orderUuid) {
        return ResponseEntity.ok(orderService.getOrderByUuid(orderUuid));
    }

    @PutMapping("/update-status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(request.getOrderUuid(), request.getStatus()));
    }

    @DeleteMapping("/cancel/{orderUuid}")
    public ResponseEntity<Void> cancelOrder(@PathVariable String orderUuid) {
        orderService.cancelOrder(orderUuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history")
    public ResponseEntity<OrderDTO> getOrderHistory(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(orderService.getOrderHistory(user));
    }
}
