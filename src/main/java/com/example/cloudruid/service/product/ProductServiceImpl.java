package com.example.cloudruid.service.product;

import com.example.cloudruid.model.entity.Product;
import com.example.cloudruid.model.exception.ItemNotFoundException;
import com.example.cloudruid.model.service.product.ProductAddServiceModel;
import com.example.cloudruid.repository.ProductRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Set<Product> getAllProducts() {
        return null;
    }

    @Override
    public Boolean existsByName(String name) {
        return this.productRepository.existsByName(name);
    }

    @Override
    public void addProduct(ProductAddServiceModel productAddServiceModel) {
        Product product = new Product();

        product
                .setName(productAddServiceModel.getName())
                .setPrice(productAddServiceModel.getPrice());

        this.productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return this.productRepository.findProductById(id)
                .orElseThrow(() -> new ItemNotFoundException("Product with id " + id + " was not found"));
    }

    @Override
    public void initializeProducts() {
        if(productRepository.count() == 0) {
            List<Product> products = new ArrayList<>();

            Product apple = new Product();

            apple
                    .setName("Apple")
                    .setPrice(BigDecimal.valueOf(0.5));

            products.add(apple);

            Product banana = new Product();

            banana
                    .setName("Banana")
                    .setPrice(BigDecimal.valueOf(0.4));

            products.add(banana);

            Product tomato = new Product();

            tomato
                    .setName("Tomato")
                    .setPrice(BigDecimal.valueOf(0.3));

            products.add(tomato);

            Product potato = new Product();

            potato
                    .setName("Potato")
                    .setPrice(BigDecimal.valueOf(0.26));

            products.add(potato);

            this.productRepository.saveAll(products);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        this.productRepository.deleteProductEntityById(id);
    }


}
