package com.engati.ecommerce.model.entity;

import jakarta.persistence.*;

import java.net.URL;

@Entity
@Table(name="Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String usp;

    private String description;
    private URL url;
    private Double price;

    private Integer stock;

    private String category;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
}
