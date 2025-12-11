package com.gibesystems.ecommerce.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gibesystems.ecommerce.category.dto.CategoryRequest;
import com.gibesystems.ecommerce.category.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    Page<CategoryResponse> getAllCategories(Pageable pageable);
    CategoryResponse getCategoryById(Long id);
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
}
