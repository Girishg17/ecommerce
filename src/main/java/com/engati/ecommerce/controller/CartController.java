package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.dto.CartItemDto;
import com.engati.ecommerce.request.QuantityReq;
import com.engati.ecommerce.responses.CartResponse;
import com.engati.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable Long userId) {
        CartResponse c = cartService.getCartByUserId(userId);

        return c;

    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addToCart(@PathVariable Long userId, @RequestBody CartItemDto cartItemDTO) {
        String message = cartService.addToCart(userId, cartItemDTO);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public void removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        cartService.removeFromCart(userId, productId);
    }

    @PutMapping("/{userId}/update/{productId}")
    public void updateCartItem(@PathVariable Long userId, @PathVariable Long productId, @RequestBody QuantityReq quantityReq) {
        Integer q = quantityReq.getQuantity();
        if (q > 0) {
            cartService.updateCartItem(userId, productId, q);
        } else {
            cartService.removeFromCart(userId, productId);
        }
    }
}
