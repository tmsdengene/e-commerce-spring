package com.gibesystems.ecommerce.cart;

import com.gibesystems.ecommerce.auth.entity.User;
import org.springframework.security.core.Authentication;
import com.gibesystems.ecommerce.cart.dto.*;
import com.gibesystems.ecommerce.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cartService.getCartByUser(user));
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addItem(Authentication authentication,
            @RequestBody AddCartItemRequest request) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cartService.addItemToCart(user, request));
    }

    @PutMapping("/update")
    public ResponseEntity<CartDTO> updateItem(Authentication authentication,
            @RequestBody UpdateCartItemRequest request) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cartService.updateCartItem(user, request));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartDTO> removeItem(Authentication authentication,
            @RequestBody RemoveCartItemRequest request) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cartService.removeCartItem(user, request));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }
}
