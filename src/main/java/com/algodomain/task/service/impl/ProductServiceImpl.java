package com.algodomain.task.service.impl;

import com.algodomain.task.exceptions.ResourceNotFoundException;
import com.algodomain.task.model.Product;
import com.algodomain.task.repository.ProductRepository;
import com.algodomain.task.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product does not exist"));
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product product1 = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product does not exist"));
        product1.setProductId(productId);
        product1.setProductName(product.getProductName());
        product1.setProductDesc(product.getProductDesc());
        product1.setPrice(product.getPrice());
        product1.setQuantity(product.getQuantity());
        return this.productRepository.save(product1);
    }

    @Override
    public boolean deleteProduct(Long productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product does not exist"));
        this.productRepository.delete(product);
        return true;
    }
}
