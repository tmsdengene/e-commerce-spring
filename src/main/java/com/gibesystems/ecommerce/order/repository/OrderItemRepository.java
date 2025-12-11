package com.gibesystems.ecommerce.order.repository;

import com.gibesystems.ecommerce.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
