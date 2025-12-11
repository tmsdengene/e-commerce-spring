package com.gibesystems.ecommerce.order.repository;

import com.gibesystems.ecommerce.order.entity.Order;
import com.gibesystems.ecommerce.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderUuid(String orderUuid);
    List<Order> findByUser(User user);
}
