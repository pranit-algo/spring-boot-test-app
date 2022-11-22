package com.algodomain.task.controller;


import com.algodomain.task.model.Product;
import com.algodomain.task.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ProductController.class})
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.algodomain.task")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    private void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    @Order(1)
    void getAllProductsTest() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product(101l, "Hp Laptop", "High end laptop for coders", 20000d, 30));
        products.add(new Product(102l, "Dell Laptop", "High end laptop for professionals", 50000d, 20));
        products.add(new Product(103l, "Lenovo Laptop", "High end laptop for gamers", 70000d, 40));
        when(productService.getAllProducts()).thenReturn(products);
        this.mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(2)
    void getProductByIdTest() throws Exception {
        Product product = new Product(101l, "Hp Laptop", "High end laptop for coders", 20000d, 30);
        Long productId = 101l;
        when(productService.getProductById(productId)).thenReturn(product);
        this.mockMvc.perform(get("/api/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".productId").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath(".productName").value("Hp Laptop"))
                .andExpect(MockMvcResultMatchers.jsonPath(".productDesc").value("High end laptop for coders"))
                .andExpect(MockMvcResultMatchers.jsonPath(".price").value(20000d))
                .andExpect(MockMvcResultMatchers.jsonPath(".quantity").value(30))
                .andDo(print());
    }

    @Test
    @Order(3)
    void createProductTest() throws Exception {
        Product product = new Product();
        product.setProductId(101l);
        product.setProductName("HP Laptop");
        product.setProductDesc("Test Description");
        product.setPrice(50000D);
        product.setQuantity(10);
        when(productService.createProduct(product)).thenReturn(product);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(product);
        this.mockMvc.perform(post("/api/products")
                .content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(4)
    void updateProductTest() throws Exception {
        Product product = new Product(101l, "Hp Laptop", "High end laptop for coders", 20000d, 30);
        Long productId = 101l;
        when(productService.getProductById(productId)).thenReturn(product);

        product.setProductName("Dell laptop");
        product.setProductDesc("Laptop for working professionals");
        when(productService.updateProduct(productId, product)).thenReturn(product);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(product);
        System.out.println(jsonBody);
        this.mockMvc.perform(put("/api/products/{productId}", productId)
                .content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath(".productId").value(productId))
//                .andExpect(MockMvcResultMatchers.jsonPath(".productName").value(product.getProductName()))
//                .andExpect(MockMvcResultMatchers.jsonPath(".productDesc").value(product.getProductDesc()))
//                .andExpect(MockMvcResultMatchers.jsonPath(".price").value(product.getPrice()))
//                .andExpect(MockMvcResultMatchers.jsonPath(".quantity").value(product.getQuantity()))
                .andDo(print());
    }

    @Test
    void deleteProductTest() throws Exception {
        Product product = new Product(101l, "Hp Laptop", "High end laptop for coders", 20000d, 30);
        Long productId = 101l;
        when(productService.getProductById(productId)).thenReturn(product);
        this.mockMvc.perform(delete("/api/products/{productId}", productId))
                .andExpect(status().isOk());
    }
}
