package com.example.cloudruid.model.service.order;

import java.util.List;

public class OrderAddServiceModel {

    private List<String> products;

    private List<String> discounts;

    public List<String> getProducts() {
        return products;
    }

    public OrderAddServiceModel setProducts(List<String> products) {
        this.products = products;
        return this;
    }

    public List<String> getDiscounts() {
        return discounts;
    }

    public OrderAddServiceModel setDiscounts(List<String> discounts) {
        this.discounts = discounts;
        return this;
    }
}
