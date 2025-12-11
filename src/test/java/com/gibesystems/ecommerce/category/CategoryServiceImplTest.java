package com.gibesystems.ecommerce.category;

import com.gibesystems.ecommerce.category.Category;
import com.gibesystems.ecommerce.category.CategoryRepository;
import com.gibesystems.ecommerce.category.CategoryServiceImpl;
import com.gibesystems.ecommerce.category.dto.CategoryRequest;
import com.gibesystems.ecommerce.category.dto.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = Category.builder().id(1L).name("Electronics").build();
    }

    @Test
    void testCreateCategory() {
        CategoryRequest request = CategoryRequest.builder().name("Electronics").build();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        CategoryResponse response = categoryService.createCategory(request);
        assertNotNull(response);
        assertEquals("Electronics", response.getName());
    }

    @Test
    void testGetCategoryById_Found() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        CategoryResponse response = categoryService.getCategoryById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(com.gibesystems.ecommerce.exception.ValidationException.class, () -> {
            categoryService.getCategoryById(2L);
        });
    }

    @Test
    void testGetAllCategories() {
        Pageable pageable = mock(Pageable.class);
        Page<Category> categoryPage = new PageImpl<>(Arrays.asList(category));
        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        Page<CategoryResponse> responses = categoryService.getAllCategories(pageable);
        assertNotNull(responses);
        assertEquals(1, responses.getContent().size());
        assertEquals("Electronics", responses.getContent().get(0).getName());
    }

    @Test
    void testUpdateCategory() {
        CategoryRequest request = CategoryRequest.builder().name("Home").build();
        Category updated = Category.builder().id(1L).name("Home").build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(updated);
        CategoryResponse response = categoryService.updateCategory(1L, request);
        assertNotNull(response);
        assertEquals("Home", response.getName());
    }

    @Test
    void testDeleteCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);
        assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
    }
}
