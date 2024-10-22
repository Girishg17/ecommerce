package com.engati.ecommerce.controller;

import com.engati.ecommerce.model.dto.ProductDto;
import com.engati.ecommerce.model.entity.Product;
import com.engati.ecommerce.request.ProductRequest;
import com.engati.ecommerce.responses.AllProductRes;
import com.engati.ecommerce.responses.ProdResponse;
import com.engati.ecommerce.responses.ProductResponse;
import com.engati.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public List<AllProductRes> findAllProducts() {
        return productService.getAllProduct();
    }

    @GetMapping("/{merchantId}/products")
    public ResponseEntity<List<ProdResponse>> getAllProductsOfMerchant(@PathVariable Long merchantId) {
        List<ProdResponse> products = productService.getAllProductOfMerchant(merchantId);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully.");
    }

    @PutMapping("/update/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody ProductDto  productDto) {
        Product updatedProduct = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(updatedProduct);
    }
}
