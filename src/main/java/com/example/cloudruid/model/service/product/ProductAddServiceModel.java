package com.example.cloudruid.model.service.product;

import java.math.BigDecimal;

public class ProductAddServiceModel {

    private String name;

    private BigDecimal price;

    public String getName() {
        return name;
    }

    public ProductAddServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductAddServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
