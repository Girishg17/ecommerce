package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.dto.ProductDto;
import com.engati.ecommerce.model.entity.Merchant;
import com.engati.ecommerce.model.entity.Product;
import com.engati.ecommerce.request.ProductRequest;
import com.engati.ecommerce.service.CloudinaryService;
import com.engati.ecommerce.service.MerchantService;
import com.engati.ecommerce.service.ProductService;
import com.engati.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/service/merchant")
public class MerchantController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MerchantService merchantService;



    @PostMapping("/{merchantId}/upload")
    public void uploadProduct(
            @PathVariable Long merchantId,
            @ModelAttribute ProductDto productDto
    ) {
        productService.addproduct(merchantId, productDto);
    }

    @PostMapping("/{merchantId}/upld")
    public ResponseEntity<String> uploadfile(
            @ModelAttribute ProductRequest productRequest,
            @PathVariable Long merchantId
    ) throws IOException {
        productService.addproductswithCloudinary(productRequest,merchantId);
        return ResponseEntity.ok("Product created successfully");

    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }


}
