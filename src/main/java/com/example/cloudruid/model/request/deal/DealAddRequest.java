package com.example.cloudruid.model.request.deal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class DealAddRequest {

    @NotBlank
    private String name;

    @Valid
    private List<String> products;

    public String getName() {
        return name;
    }

    public DealAddRequest setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getProducts() {
        return products;
    }

    public DealAddRequest setProducts(List<String> products) {
        this.products = products;
        return this;
    }
}
