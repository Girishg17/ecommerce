package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.dto.CartDTO;
import com.engati.ecommerce.model.dto.CartItemDto;
import com.engati.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public CartDTO getCart(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public void addToCart(@PathVariable Long userId, @RequestBody CartItemDto cartItemDTO) {
        cartService.addToCart(userId, cartItemDTO);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public void removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        cartService.removeFromCart(userId, productId);
    }

    @PutMapping("/{userId}/update/{productId}")
    public void updateCartItem(@PathVariable Long userId, @PathVariable Long productId, @RequestParam Integer quantity) {
        cartService.updateCartItem(userId, productId, quantity);
    }
}
