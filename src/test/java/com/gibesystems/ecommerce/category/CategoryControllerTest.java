package com.gibesystems.ecommerce.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibesystems.ecommerce.category.dto.CategoryRequest;
import com.gibesystems.ecommerce.category.dto.CategoryResponse;
import com.gibesystems.ecommerce.category.CategoryController;
import com.gibesystems.ecommerce.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import static org.mockito.Mockito.doNothing;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private ObjectMapper objectMapper;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
        categoryResponse = CategoryResponse.builder().id(1L).name("Electronics").build();
    }

    @Test
    void testCreateCategory() throws Exception {
        CategoryRequest request = CategoryRequest.builder().name("Electronics").build();
        when(categoryService.createCategory(any(CategoryRequest.class))).thenReturn(categoryResponse);
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test
    void testGetCategoryById() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(categoryResponse);
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    // @Test
    // void testGetAllCategories() throws Exception {
    //     Page<CategoryResponse> page = new PageImpl<>(Arrays.asList(categoryResponse));
    //     when(categoryService.getAllCategories(any(Pageable.class)))
    //         .thenReturn(page);
    //     mockMvc.perform(get("/categories"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.content[0].name").value("Electronics"))
    //             .andExpect(jsonPath("$.size").exists())
    //             .andExpect(jsonPath("$.totalElements").value(1));
    // }

    @Test
    void testUpdateCategory() throws Exception {
        CategoryRequest request = CategoryRequest.builder().name("Home").build();
        CategoryResponse updated = CategoryResponse.builder().id(1L).name("Home").build();
        when(categoryService.updateCategory(1L, request)).thenReturn(updated);
        mockMvc.perform(put("/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Home"));
    }

    @Test
    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(1L);
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent());
    }
}
