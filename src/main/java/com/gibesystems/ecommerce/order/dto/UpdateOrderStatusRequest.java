package com.gibesystems.ecommerce.order.dto;

public class UpdateOrderStatusRequest {
    private String orderUuid;
    private String status;

    // Getters and setters
    public String getOrderUuid() { return orderUuid; }
    public void setOrderUuid(String orderUuid) { this.orderUuid = orderUuid; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
