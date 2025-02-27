package com.example.cloudruid.model.request.order;

import javax.validation.Valid;
import java.util.List;

public class OrderAddRequest {

    @Valid
    private List<String> products;
    @Valid
    private List<String> discounts;

    public List<String> getProducts() {
        return products;
    }

    public OrderAddRequest setProducts(List<String> products) {
        this.products = products;
        return this;
    }

    public List<String> getDiscounts() {
        return discounts;
    }

    public OrderAddRequest setDiscounts(List<String> discounts) {
        this.discounts = discounts;
        return this;
    }
}
