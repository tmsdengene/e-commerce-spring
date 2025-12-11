package com.gibesystems.ecommerce.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByActiveTrue();
    Page<Product> findByActiveTrue(Pageable pageable);
    // Add more query methods as needed
}
