package com.example.cloudruid.repository;

import com.example.cloudruid.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductById(Long id);

    Boolean existsByName(String name);

    @Transactional
    @Modifying
    void deleteProductEntityById(Long id);
}
