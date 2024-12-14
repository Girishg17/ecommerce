package com.engati.ecommerce.responses;

import com.engati.ecommerce.service.CategoryService;
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
//    public Presponse(ProductsDto product, OrderItem orderItem, CategoryService categoryService) {
//        this.productId = product.getId();
//        this.productName = product.getName();
//        this.price = product.getPrice();
//        this.quantity = orderItem.getQuantity();
//        this.file=product.getFile();
//        this.description=product.getDescription();
//
//        this.rating=product.getRating();
//        this.totalRating=product.getRatingCount();
//        Optional<Category> categoryDTO = categoryService.findById(product.getCategoryId());
//        this.category = categoryDTO != null ? categoryDTO.get().getName() : "Unknown";
//    }

    public Presponse(String name, String usp, String description, String file,
                     Double price, Integer stock, Double rating, Integer ratingCount,
                     Long merchantId, Long categoryId, boolean deleted,
                     Integer quantity, Long productId, CategoryService categoryService) {
        this.productName = name;
//        this. = usp;
        this.productId=productId;
        this.description = description;
        this.file = file;
        this.price = price;
//        this.stock = stock;
        this.rating = rating;
        this.totalRating = ratingCount;
        this.quantity=quantity;
//        this.merchantId = merchantId;
//        this.categoryId = categoryId;
//        this.deleted = deleted;

        // You can utilize categoryService here if needed
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
