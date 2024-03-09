package com.krishna.productService_sst.services;

import com.krishna.productService_sst.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getSingleProduct(Long id);

    Product createProduct(Product product);

    Product updateProduct(Product product);
    void deleteProduct(Long id);
    List<Product> getProductsByCategory(String categoryName);
    List<String> getCategories();


}