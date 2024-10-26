package com.engati.ecommerce.responses;

import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.model.entity.Product;
import com.engati.ecommerce.model.entity.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Presponse {

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("file")
    private String file;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;

    @JsonProperty("rating")
    private double rating;

    @JsonProperty("totalRating")
    private Integer totalRating;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


    // Constructor
    public Presponse(Product product, OrderItem orderItem) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.price = product.getPrice();
        this.quantity = orderItem.getQuantity();
        this.file=product.getFile();
        this.description=product.getDescription();
        this.category=product.getCategory().getName();
        this.rating=product.getRating();
        this.totalRating=product.getRatingCount();

    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
