package com.engati.ecommerce.service;

import com.engati.ecommerce.model.dto.CartDTO;
import com.engati.ecommerce.model.dto.CartItemDto;

public interface CartService {
    public CartDTO getCartByUserId(Long userId);
    public void addToCart(Long userId, CartItemDto cartItemDTO);
    public void removeFromCart(Long userId, Long productId);
    public void updateCartItem(Long userId, Long productId, Integer quantity);
}
