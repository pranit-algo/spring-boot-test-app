package com.algodomain.task.service;

import com.algodomain.task.exceptions.ResourceNotFoundException;
import com.algodomain.task.model.Product;
import com.algodomain.task.repository.ProductRepository;
import com.algodomain.task.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

//    @InjectMocks
//    private MockMvc mockMvc;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        this.productService = new ProductServiceImpl(this.productRepository);
    }

    @Test
    void testGetAllProducts() {
        productService.getAllProducts();
        verify(productRepository).findAll();
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductId(101l);
        product.setProductName("HP Laptop");
        product.setProductDesc("Test Description");
        product.setPrice(50000D);
        product.setQuantity(10);

        when(productRepository.save(product)).thenReturn(product);
        assertThat(productService.createProduct(product)).isEqualTo(product);
    }

    @Test()
    void testGetProductById() {
        Product product = new Product();
        product.setProductId(101l);
        product.setProductName("HP Laptop");
        product.setProductDesc("Test Description");
        product.setPrice(50000D);
        product.setQuantity(10);
//        productRepository.save(product);

        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
        assertThat(productService.getProductById(product.getProductId())).isEqualTo(product);
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(null);
        });

        assertEquals("product does not exist", exception.getMessage());
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setProductId(101l);
        product.setProductName("HP Laptop");
        product.setProductDesc("Test Description");
        product.setPrice(50000D);
        product.setQuantity(10);

        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
        product.setProductDesc("Test Description Updated");
        product.setProductName("Test name updated");
        product.setPrice(60000D);
        product.setQuantity(20);
        when(productRepository.save(product)).thenReturn(product);
        assertThat(productService.updateProduct(product.getProductId(), product)).isEqualTo(product);
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(null, product);
        });

        assertEquals("product does not exist", exception.getMessage());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId(101l);
        product.setProductName("HP Laptop");
        product.setProductDesc("Test Description");
        product.setPrice(50000D);
        product.setQuantity(10);

        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
        assertThat(productService.deleteProduct(product.getProductId())).isTrue();
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(null);
        });

        assertEquals("product does not exist", exception.getMessage());
    }
}

//    @Test
//    void testResourceNotFoundException() {
////        Product product = new Product();
//////        product.setProductId(101l);
////        product.setProductName("HP Laptop");
////        product.setProductDesc("Test Description");
////        product.setPrice(50000D);
////        product.setQuantity(10);
//
//
//    }
//}