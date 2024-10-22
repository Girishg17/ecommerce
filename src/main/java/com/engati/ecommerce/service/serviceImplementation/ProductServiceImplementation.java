package com.engati.ecommerce.service.serviceImplementation;

import com.engati.ecommerce.model.dto.ProductDto;
import com.engati.ecommerce.model.entity.Category;
import com.engati.ecommerce.model.entity.Merchant;
import com.engati.ecommerce.model.entity.Product;
import com.engati.ecommerce.repository.CategoryRepository;  // <-- Add Category Repository
import com.engati.ecommerce.repository.MerchantRepository;
import com.engati.ecommerce.repository.ProductRepository;
import com.engati.ecommerce.request.ProductRequest;
import com.engati.ecommerce.responses.AllProductRes;
import com.engati.ecommerce.responses.ProdResponse;
import com.engati.ecommerce.service.CloudinaryService;
import com.engati.ecommerce.service.MerchantService;
import com.engati.ecommerce.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public void addproduct(Long merchantId, ProductDto pdto) {

        Merchant merchant = merchantService.findMerchantById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        Category category = categoryRepository.findById(pdto.getCategoryId())  // <-- Handle Category
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = modelMapper.map(pdto, Product.class);
        product.setMerchant(merchant);
        product.setCategory(category);  // <-- Set Category
        productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<AllProductRes> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<AllProductRes> allProductResponses = new ArrayList<>();
        for (Product product : products) {
            System.out.println("category name"+product.getCategory().getName());
            AllProductRes response = modelMapper.map(product, AllProductRes.class);
            response.setCategory(product.getCategory().getName());
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

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = modelMapper.map(productRequest, Product.class);
        product.setMerchant(merchant);
        product.setCategory(category);
        product.setFile(imageUrl);
        System.out.println("uploaded image url" + imageUrl);
        productRepository.save(product);
    }
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Long id, ProductDto dto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.getName() != null) {
            existingProduct.setName(dto.getName());
        }
        if (dto.getUsp() != null) {
            existingProduct.setUsp(dto.getUsp());
        }
        if (dto.getDescription() != null) {
            existingProduct.setDescription(dto.getDescription());
        }
//        if (dto.getFile() != null) {
//            Category category = categoryRepository.findById(dto.getCategoryId())
//                    .orElseThrow(() -> new RuntimeException("Category not found"));
//            existingProduct.setCategory(category);
//        }
        if (dto.getPrice() != null) {
            existingProduct.setPrice(dto.getPrice());
        }
        if (dto.getStock() != null) {
            existingProduct.setStock(dto.getStock());
        }

        return productRepository.save(existingProduct);
    }
}




