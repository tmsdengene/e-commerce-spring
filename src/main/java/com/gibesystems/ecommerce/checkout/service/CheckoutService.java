package com.gibesystems.ecommerce.checkout.service;

import com.gibesystems.ecommerce.checkout.dto.CheckoutDTO;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.entity.Order;

public interface CheckoutService {
    CheckoutDTO checkout(User user, Order order, String paymentMethod);
    CheckoutDTO getCheckoutByOrder(Order order);
    CheckoutDTO getLatestCheckoutForUser(User user);
}
