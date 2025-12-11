package com.gibesystems.ecommerce.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gibesystems.ecommerce.category.dto.CategoryRequest;
import com.gibesystems.ecommerce.category.dto.CategoryResponse;
import com.gibesystems.ecommerce.exception.ValidationException;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new ValidationException("Parent category not found"));
            category.setParent(parent);
        }
        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
            .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ValidationException("Category not found"));
        return toResponse(category);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ValidationException("Category not found"));
        category.setName(request.getName());
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new ValidationException("Parent category not found"));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }
        Category updated = categoryRepository.save(category);
        return toResponse(updated);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ValidationException("Category not found"));
        boolean hasSubcategories = category.getSubcategories() != null && !category.getSubcategories().isEmpty();
        if (hasSubcategories) {
            throw new ValidationException("Cannot delete category with subcategories.");
        }
        // TODO: Check for products in this category before deleting (requires ProductRepository)
        categoryRepository.delete(category);
    }

    private CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setParentName(category.getParent() != null ? category.getParent().getName() : null);
        return response;
    }
}
