package com.engati.ecommerce.service;

import com.engati.ecommerce.model.entity.Merchant;

import java.util.Optional;

public interface MerchantService {
    Optional<Merchant> findMerchantById(Long id);
}
