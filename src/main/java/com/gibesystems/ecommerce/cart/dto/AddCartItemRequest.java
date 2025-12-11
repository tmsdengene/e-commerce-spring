
package com.gibesystems.ecommerce.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCartItemRequest {
    private Long productId;
    private int quantity;
}
