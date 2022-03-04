package com.example.cloudruid.init;

import com.example.cloudruid.service.deal.DealService;
import com.example.cloudruid.service.product.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitialization implements CommandLineRunner {

    private final ProductService productService;
    private final DealService dealService;

    public DataInitialization(ProductService productService, DealService dealService) {
        this.productService = productService;
        this.dealService = dealService;
    }

    @Override
    public void run(String... args) throws Exception {
        productService.initializeProducts();
        dealService.initializeDeals();
    }
}
