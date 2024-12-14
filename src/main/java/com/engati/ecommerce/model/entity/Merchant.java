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



//    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Product> products;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }


//    public List<Product> getProducts() { return products; }
//    public void setProducts(List<Product> products) { this.products = products; }


}
