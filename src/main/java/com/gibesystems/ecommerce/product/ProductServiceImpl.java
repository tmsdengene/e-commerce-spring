    package com.gibesystems.ecommerce.product;

import com.gibesystems.ecommerce.category.Category;
import com.gibesystems.ecommerce.category.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gibesystems.ecommerce.exception.ProductNotFoundException;
import com.gibesystems.ecommerce.exception.ValidationException;
import com.gibesystems.ecommerce.product.dto.ProductRequest;
import com.gibesystems.ecommerce.product.dto.ProductResponse;
import com.gibesystems.ecommerce.shared.FileUploadService;
import com.gibesystems.ecommerce.shared.PageResponse;

import java.io.IOException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

    @Service
    public class ProductServiceImpl implements ProductService {
        private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private CategoryRepository categoryRepository;
        @Autowired
        private ProductMapping productMapping;

        @Autowired
        private FileUploadService fileUploadService;
    @Override
    public String uploadProductImage(MultipartFile file) throws IOException {
        return fileUploadService.uploadFile(file);
    }

        @Override
        public PageResponse<ProductResponse> getAllProducts(Pageable pageable) {
            Page<ProductResponse> page = productRepository.findByActiveTrue(pageable)
                    .map(productMapping::toResponse);
            return new PageResponse<>(page);
        }

        @Override
        public Optional<ProductResponse> getProductById(Long id) {
            return productRepository.findById(id).map(productMapping::toResponse);
        }

        @Override
        @Transactional
        public ProductResponse addProduct(ProductRequest productRequest) {
            try {
                validateRequest(productRequest);
                Product product = productMapping.toEntity(productRequest);
                product.setProductUuid(java.util.UUID.randomUUID().toString());
                product.setActive(true);
                // Audit logging
                product.setCreatedBy("admin"); // Replace with actual user
                product.setCreatedAt(java.time.LocalDateTime.now());
                Product saved = productRepository.save(product);
                logger.info("Product created: {} by {} at {}", saved.getName(), saved.getCreatedBy(), saved.getCreatedAt());
                return productMapping.toResponse(saved);
            } catch (Exception e) {
                logger.error("Error creating product: {}", e.getMessage());
                throw e;
            }
        }

        @Override
        @Transactional
        public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
            try {
                validateRequest(productRequest);
                Product product = productRepository.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found"));
                Product updatedEntity = productMapping.toEntity(productRequest);
                // Keep immutable fields and audit
                updatedEntity.setId(product.getId());
                updatedEntity.setProductUuid(product.getProductUuid());
                updatedEntity.setCreatedBy(product.getCreatedBy());
                updatedEntity.setCreatedAt(product.getCreatedAt());
                updatedEntity.setActive(product.isActive());
                updatedEntity.setUpdatedBy("admin"); // Replace with actual user
                updatedEntity.setUpdatedAt(java.time.LocalDateTime.now());
                Product updated = productRepository.save(updatedEntity);
                logger.info("Product updated: {} by {} at {}", updated.getName(), updated.getUpdatedBy(), updated.getUpdatedAt());
                return productMapping.toResponse(updated);
            } catch (Exception e) {
                logger.error("Error updating product: {}", e.getMessage());
                throw e;
            }
        }

        @Override
        @Transactional
        public void deactivateProduct(Long id) {
            productRepository.findById(id).ifPresent(product -> {
                product.setActive(false);
                productRepository.save(product);
            });
        }

        @Override
        @Transactional
        public void deleteProduct(Long id) {
            productRepository.deleteById(id);
        }

        @Override
        @Transactional
        public void reduceStock(Long productId, int quantity) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));
            if (product.getStockQuantity() == null || product.getStockQuantity() < quantity) {
                throw new ValidationException("Insufficient stock");
            }
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product);
            logger.info("Stock reduced for product {} by {} units", product.getName(), quantity);
        }

        @Override
        @Transactional
        public void restoreStock(Long productId, int quantity) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));
            product.setStockQuantity(product.getStockQuantity() + quantity);
            productRepository.save(product);
            logger.info("Stock restored for product {} by {} units", product.getName(), quantity);
        }

        @Override
        public PageResponse<ProductResponse> searchProducts(String name, String category, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDir) {
            Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            // Basic filtering using repository and stream (for demo, use Specification for complex queries)
            Page<Product> products = productRepository.findByActiveTrue(pageable);
            Page<ProductResponse> filteredPage = products.stream()
                .filter(p -> name == null || p.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(p -> category == null || (p.getCategory() != null && p.getCategory().getName().equalsIgnoreCase(category)))
                .filter(p -> minPrice == null || p.getPrice().doubleValue() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice().doubleValue() <= maxPrice)
                .map(productMapping::toResponse)
                .collect(java.util.stream.Collectors.collectingAndThen(
                    java.util.stream.Collectors.toList(),
                    list -> new org.springframework.data.domain.PageImpl<>(list, pageable, products.getTotalElements())
                ));
            return new PageResponse<>(filteredPage);
        }

        private void validateRequest(ProductRequest productRequest) {
            if (productRequest.getName() == null || productRequest.getName().trim().isEmpty()) {
                throw new ValidationException("Product name is required");
            }
            if (productRequest.getCategoryId() == null) {
                throw new ValidationException("Category is required");
            }
            if (productRequest.getPrice() == null) {
                throw new ValidationException("Price is required");
            }
            if (productRequest.getStockQuantity() == null) {
                throw new ValidationException("Stock quantity is required");
            }
            if (productRequest.getDescription() == null || productRequest.getDescription().trim().isEmpty()) {
                throw new ValidationException("Description is required");
            }
            if (productRequest.getImageUrl() == null || productRequest.getImageUrl().trim().isEmpty()) {
                throw new ValidationException("At least one image is required");
            }
        }

        // Mapping moved to ProductMapping class
    }
