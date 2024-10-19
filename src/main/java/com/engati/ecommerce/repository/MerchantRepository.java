package com.engati.ecommerce.repository;

import com.engati.ecommerce.model.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {
}
