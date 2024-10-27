package com.engati.ecommerce.responses;

import java.util.List;

public class CartResponse {

private List<CartItemResponse> items; // List of items in the cart

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }
}

