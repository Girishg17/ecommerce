package com.engati.ecommerce.service.serviceImplementation;


import com.engati.ecommerce.model.dto.ProductDto;
import com.engati.ecommerce.model.entity.Merchant;
import com.engati.ecommerce.model.entity.Product;
import com.engati.ecommerce.repository.MerchantRepository;
import com.engati.ecommerce.repository.ProductRepository;
import com.engati.ecommerce.request.ProductRequest;
import com.engati.ecommerce.responses.AllProductRes;
import com.engati.ecommerce.responses.ProdResponse;
import com.engati.ecommerce.service.CloudinaryService;
import com.engati.ecommerce.service.MerchantService;
import com.engati.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    public void addproduct(Long merchantId, ProductDto pdto) {

        Merchant merchant = merchantService.findMerchantById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        Product product = modelMapper.map(pdto, Product.class);
        product.setMerchant(merchant);
        productRepository.save(product);

    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<AllProductRes> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<AllProductRes> allProductResponses = new ArrayList<>();
        for (Product product : products) {
            AllProductRes response = modelMapper.map(product, AllProductRes.class);

            Merchant merchant = merchantRepository.findById(product.getMerchant().getId()).orElse(null);
            if (merchant != null) {
                response.setRating(merchant.getRating());
            } else {
                response.setRating(0.0);
            }

            allProductResponses.add(response);
        }

        return allProductResponses;
    }

    @Override
    public List<ProdResponse> getAllProductOfMerchant(Long merchantId) {

        List<Product> products = productRepository.findByMerchantId(merchantId);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }

    private ProdResponse convertToResponse(Product product) {
        return modelMapper.map(product, ProdResponse.class);
    }

    @Override
    public void addproductswithCloudinary(ProductRequest productRequest, Long merchantId) throws IOException {
        String imageUrl = cloudinaryService.upload(productRequest.getImage());
        Merchant merchant = merchantService.findMerchantById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        Product product = modelMapper.map(productRequest, Product.class);
        product.setMerchant(merchant);
        product.setFile(imageUrl);
        System.out.println("uploaded image url" + imageUrl);
        productRepository.save(product);

    }

}

