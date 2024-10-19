package com.engati.ecommerce.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Merchants")
public class Merchant {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private Double rating;
    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    public void setUser(User user) {
        this.user=user;
    }

    public void setRating(double rating) {
        this.rating=rating;
    }
}
