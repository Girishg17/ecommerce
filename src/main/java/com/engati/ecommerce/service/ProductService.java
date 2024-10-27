package com.engati.ecommerce.service;

import com.engati.ecommerce.model.dto.ProductDto;
import com.engati.ecommerce.model.entity.Product;
import com.engati.ecommerce.model.entity.ProductDocument;
import com.engati.ecommerce.request.ProdReq;
import com.engati.ecommerce.request.ProductRequest;
import com.engati.ecommerce.responses.AllProductRes;
import com.engati.ecommerce.responses.ProdResponse;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    public Product getProductById(Long id);
    public List<AllProductRes> getAllProduct();
    public void addproduct(Long id,ProductDto dto);
    public  List<ProdResponse> getAllProductOfMerchant(Long merchantId);
    public void addproductswithCloudinary(ProductRequest preq,Long id) throws IOException;
    public void deleteProduct(Long id);
    public void updateProduct(Long id, ProdReq p)throws IOException;
    public List<ProductDocument> searchProducts(String name);
    public void updateStockofProduct(Product p);
    public List<AllProductRes> getProductByCategory(Long Id);
    public void deleteAllProducts();
    public void updateProductRating(Long ProductId,double rating);
}
