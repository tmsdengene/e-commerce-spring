package com.gibesystems.ecommerce.order.service;

import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.dto.*;

public interface OrderService {
    OrderDTO createOrder(User user, CreateOrderRequest request);
    OrderDTO getOrderByUuid(String orderUuid);
    OrderDTO updateOrderStatus(String orderUuid, String status);
    void cancelOrder(String orderUuid);
    OrderDTO getOrderHistory(User user);
}
