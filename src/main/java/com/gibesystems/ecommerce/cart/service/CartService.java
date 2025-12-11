package com.gibesystems.ecommerce.cart.service;

import com.gibesystems.ecommerce.cart.dto.*;
import com.gibesystems.ecommerce.cart.entity.Cart;
import com.gibesystems.ecommerce.auth.entity.User;

public interface CartService {
    CartDTO getCartByUser(User user);
    CartDTO addItemToCart(User user, AddCartItemRequest request);
    CartDTO updateCartItem(User user, UpdateCartItemRequest request);
    CartDTO removeCartItem(User user, RemoveCartItemRequest request);
    void clearCart(User user);
}
