package com.engati.ecommerce.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "products")
public class ProductDocument {

    @Id
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer stock;

    private Long merchantId;
    private String merchantName;
    private Integer merchantTotalProducts;
    private Integer merchantTotalOrders;
    private Double productRating;
    private String file;

    public Double getProductRating() {
        return productRating;
    }

    public void setProductRating(Double productRating) {
        this.productRating = productRating;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }



    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }


    public Integer getMerchantTotalProducts() {
        return merchantTotalProducts;
    }

    public void setMerchantTotalProducts(Integer merchantTotalProducts) {
        this.merchantTotalProducts = merchantTotalProducts;
    }

    public Integer getMerchantTotalOrders() {
        return merchantTotalOrders;
    }

    public void setMerchantTotalOrders(Integer merchantTotalOrders) {
        this.merchantTotalOrders = merchantTotalOrders;
    }


// Getters and setters
}
