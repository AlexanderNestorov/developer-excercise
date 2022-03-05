package com.example.cloudruid.model.request.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductAddRequest {

    @NotBlank
    private String name;

    @Min(0)
    @NotNull
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public ProductAddRequest setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductAddRequest setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
