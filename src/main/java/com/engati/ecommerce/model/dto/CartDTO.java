package com.engati.ecommerce.model.dto;

import com.engati.ecommerce.model.dto.CartItemDto;

import java.util.List;

public class CartDTO {
    private Long cartId;
    private Long userId;
    private List<CartItemDto> items;

    // Getters and Setters

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}
