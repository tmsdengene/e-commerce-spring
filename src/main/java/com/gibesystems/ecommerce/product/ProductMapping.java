package com.gibesystems.ecommerce.product;

import com.gibesystems.ecommerce.category.Category;
import com.gibesystems.ecommerce.category.CategoryRepository;
import com.gibesystems.ecommerce.product.dto.ProductRequest;
import com.gibesystems.ecommerce.product.dto.ProductResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapping {
    @Autowired
    private CategoryRepository categoryRepository;

    public Product toEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElse(null);
        product.setCategory(category);
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setWeight(productRequest.getWeight());
        product.setSize(productRequest.getSize());
        product.setColor(productRequest.getColor());
        return product;
    }

    public ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setProductUuid(product.getProductUuid());
        response.setName(product.getName());
        response.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setWeight(product.getWeight());
        response.setSize(product.getSize());
        response.setColor(product.getColor());
        response.setActive(product.isActive());
        response.setLowStock(product.getStockQuantity() != null && product.getStockQuantity() < 5); // threshold = 5
        return response;
    }
}
