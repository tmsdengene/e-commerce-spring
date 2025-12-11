        package com.gibesystems.ecommerce.product;

import java.io.IOException;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.gibesystems.ecommerce.product.dto.ProductRequest;
import com.gibesystems.ecommerce.product.dto.ProductResponse;
import com.gibesystems.ecommerce.shared.PageResponse;

public interface ProductService {
    PageResponse<ProductResponse> getAllProducts(Pageable pageable);
    Optional<ProductResponse> getProductById(Long id);
    ProductResponse addProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    void deactivateProduct(Long id);
    void deleteProduct(Long id);
    void reduceStock(Long productId, int quantity);
    void restoreStock(Long productId, int quantity);
    PageResponse<ProductResponse> searchProducts(String name, String category, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDir);
    String uploadProductImage(MultipartFile file) throws IOException;
}
