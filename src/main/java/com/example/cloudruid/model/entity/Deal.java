package com.example.cloudruid.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "deals")
public class Deal extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "deal_product",
            joinColumns = @JoinColumn(name = "deal_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> applicableProducts;


    public String getName() {
        return name;
    }

    public Deal setName(String name) {
        this.name = name;
        return this;
    }

    public List<Product> getProducts() {
        return applicableProducts;
    }

    public Deal setProducts(List<Product> products) {
        this.applicableProducts = products;
        return this;
    }
}
