package com.engati.ecommerce.model.entity;

import com.engati.ecommerce.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Merchant merchantDetails;

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Merchant getMerchantDetails() {
        return merchantDetails;
    }

    public void setMerchantDetails(Merchant merchantDetails) {
        this.merchantDetails = merchantDetails;
    }

    @Enumerated(EnumType.STRING)
    private Role role;




    public void setPassword(String encode) {
        this.password = encode;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public Role getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }
}
