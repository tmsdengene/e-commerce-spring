package com.gibesystems.ecommerce.cart.repository;

import com.gibesystems.ecommerce.cart.entity.Cart;
import com.gibesystems.ecommerce.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
