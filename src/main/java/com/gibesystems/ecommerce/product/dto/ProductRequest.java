package com.gibesystems.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String name;
    private Long categoryId;
    private BigDecimal price;
    private Integer stockQuantity;
    private String description;
    private String imageUrl;
    private String weight;
    private String size;
    private String color;
}
