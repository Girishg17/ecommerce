package com.engati.ecommerce.responses;

import java.util.List;

public class CartResponse {
//    private Long productId;       // Product ID
//    private String productName;   // Product name
//    private String productUsp;    // Unique Selling Proposition
//    private String productDescription; // Product description
//    private String productFile;    // Product image or file path
//    private Double productPrice;   // Product price
//    private Integer productStock;   // Product stock
//    private Integer quantity;       // Quantity in cart
//
//    // Getters and Setters
//    public Long getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public String getProductUsp() {
//        return productUsp;
//    }
//
//    public void setProductUsp(String productUsp) {
//        this.productUsp = productUsp;
//    }
//
//    public String getProductDescription() {
//        return productDescription;
//    }
//
//    public void setProductDescription(String productDescription) {
//        this.productDescription = productDescription;
//    }
//
//    public String getProductFile() {
//        return productFile;
//    }
//
//    public void setProductFile(String productFile) {
//        this.productFile = productFile;
//    }
//
//    public Double getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(Double productPrice) {
//        this.productPrice = productPrice;
//    }
//
//    public Integer getProductStock() {
//        return productStock;
//    }
//
//    public void setProductStock(Integer productStock) {
//        this.productStock = productStock;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
private List<CartItemResponse> items; // List of items in the cart

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }
}

