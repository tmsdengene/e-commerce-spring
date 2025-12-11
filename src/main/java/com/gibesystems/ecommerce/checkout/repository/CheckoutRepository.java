package com.gibesystems.ecommerce.checkout.repository;

import com.gibesystems.ecommerce.checkout.entity.Checkout;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Optional<Checkout> findByOrder(Order order);
    List<Checkout> findByUser(User user);
}
