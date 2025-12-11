package com.gibesystems.ecommerce.category.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String name;
    private Long parentId;
    // Lombok @Data generates getters/setters
}
