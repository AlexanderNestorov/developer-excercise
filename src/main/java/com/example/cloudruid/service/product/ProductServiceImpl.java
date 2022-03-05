package com.example.cloudruid.service.product;

import com.example.cloudruid.model.entity.Product;
import com.example.cloudruid.model.exception.ItemNotFoundException;
import com.example.cloudruid.model.service.product.ProductAddServiceModel;
import com.example.cloudruid.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Set<Product> getAllProducts() {
        return new LinkedHashSet<>(this.productRepository.findAll());
    }

    @Override
    public Boolean existsByName(String name) {
        return this.productRepository.existsByName(name);
    }

    @Override
    public void addProduct(ProductAddServiceModel productAddServiceModel) {
        createProduct(productAddServiceModel.getName(), productAddServiceModel.getPrice());
    }

    @Override
    public Product getProductById(Long id) {
        return this.productRepository.findProductById(id)
                .orElseThrow(() -> new ItemNotFoundException("Product with id " + id + " was not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        this.productRepository.deleteProductEntityById(id);
    }

    @Override
    public void initializeProducts() {
        if(productRepository.count() == 0) {
             createProduct("Apple", BigDecimal.valueOf(0.5));
             createProduct("Banana", BigDecimal.valueOf(0.4));
             createProduct("Tomato", BigDecimal.valueOf(0.3));
             createProduct("Potato", BigDecimal.valueOf(0.26));
        }
    }

    private void createProduct(String name, BigDecimal price) {
        Product product = new Product();

        product
                .setName(name)
                .setPrice(price);

       this.productRepository.save(product);
    }
}
