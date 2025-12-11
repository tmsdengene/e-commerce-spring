package com.gibesystems.ecommerce.checkout.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutDTO {
    private Long id;
    private String userId;
    private Long orderId;
    private LocalDateTime checkoutTime;
    private String paymentMethod;
    private boolean paymentConfirmed;
}
