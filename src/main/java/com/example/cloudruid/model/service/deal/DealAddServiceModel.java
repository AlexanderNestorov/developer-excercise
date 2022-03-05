package com.example.cloudruid.model.service.deal;

import java.util.List;

public class DealAddServiceModel {

    private String name;

    private List<String> products;

    public String getName() {
        return name;
    }

    public DealAddServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getProducts() {
        return products;
    }

    public DealAddServiceModel setProducts(List<String> products) {
        this.products = products;
        return this;
    }
}
