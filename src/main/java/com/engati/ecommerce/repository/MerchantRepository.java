package com.engati.ecommerce.repository;

import com.engati.ecommerce.model.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {
    @Override
    Optional<Merchant> findById(Long aLong);
}
