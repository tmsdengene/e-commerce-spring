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
public class ProductResponse {
    private Long id;
    private String productUuid;
    private String name;
    private String categoryName;
    private BigDecimal price;
    private Integer stockQuantity;
    private String description;
    private String imageUrl;
    private String weight;
    private String size;
    private String color;
    private boolean active;
    private boolean lowStock;
}
