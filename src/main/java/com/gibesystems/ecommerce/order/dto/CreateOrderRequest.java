package com.gibesystems.ecommerce.order.dto;

import java.util.List;

public class CreateOrderRequest {
    private List<Long> cartItemIds;
    private String shippingAddress;
    private String paymentMethod;

    // Getters and setters
    public List<Long> getCartItemIds() { return cartItemIds; }
    public void setCartItemIds(List<Long> cartItemIds) { this.cartItemIds = cartItemIds; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
