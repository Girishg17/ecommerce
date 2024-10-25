package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.entity.Merchant;
import com.engati.ecommerce.repository.MerchantRepository;
import com.engati.ecommerce.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;


    public Optional<Merchant> findMerchantById(Long merchantId) {
          return  merchantRepository.findById(merchantId);

    }





}
