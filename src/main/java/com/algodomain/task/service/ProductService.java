package com.algodomain.task.service;

import com.algodomain.task.model.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Long productId);
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product updateProduct(Long productId, Product product);
    boolean deleteProduct(Long productId);
}
