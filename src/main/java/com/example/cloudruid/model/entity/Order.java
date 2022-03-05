package com.example.cloudruid.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @ManyToMany
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> orderedProducts;

    @Column(nullable = false)
    private BigDecimal total;

    public List<Product> getProducts() {
        return orderedProducts;
    }

    public Order setProducts(List<Product> products) {
        this.orderedProducts = products;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Order setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }
}
