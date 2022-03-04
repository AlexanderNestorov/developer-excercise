package com.example.cloudruid.service.product;

import com.example.cloudruid.model.entity.Product;
import com.example.cloudruid.model.service.product.ProductAddServiceModel;

import java.util.Set;

public interface ProductService {

    void addProduct(ProductAddServiceModel productAddServiceModel);

    Set<Product> getAllProducts();

    Boolean existsByName(String name);

    void deleteProduct(Long id);

    Product getProductById(Long id);

    void initializeProducts();
}
