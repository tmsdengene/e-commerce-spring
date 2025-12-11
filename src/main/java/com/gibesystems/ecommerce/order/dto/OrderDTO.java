package com.gibesystems.ecommerce.order.dto;

import com.gibesystems.ecommerce.order.entity.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private String orderUuid;
    private String userId;
    private List<OrderItemDTO> items;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private double totalAmount;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderUuid() { return orderUuid; }
    public void setOrderUuid(String orderUuid) { this.orderUuid = orderUuid; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
