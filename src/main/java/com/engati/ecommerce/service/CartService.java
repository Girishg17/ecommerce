package com.engati.ecommerce.service;

import com.engati.ecommerce.model.dto.CartDTO;
import com.engati.ecommerce.model.dto.CartItemDto;
import com.engati.ecommerce.model.entity.CartItem;
import com.engati.ecommerce.responses.CartResponse;

import java.util.List;

public interface CartService {
    public CartResponse getCartByUserId(Long userId);
    public String addToCart(Long userId, CartItemDto cartItemDTO);
    public void removeFromCart(Long userId, Long productId);
    public void updateCartItem(Long userId, Long productId, Integer quantity);
    List<CartItem> cartItemofUser(Long userId);
    public Long getCartId(Long userId);
    public void deleteCart(Long cartId);
}
