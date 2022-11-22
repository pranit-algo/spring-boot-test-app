package com.algodomain.task.controller;

import com.algodomain.task.model.Product;
import com.algodomain.task.service.impl.ProductServiceImpl;
import io.swagger.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = this.productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = this.productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product product1 = this.productService.createProduct(product);
        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }

    @PutMapping("{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        Product product1 = this.productService.updateProduct(productId, product);
        return new ResponseEntity<>(product1, HttpStatus.OK);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        this.productService.deleteProduct(productId);
        return new ResponseEntity<>("Product Deleted Succesfully!", HttpStatus.OK);
    }
}
