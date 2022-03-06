package com.example.cloudruid.service.order;

import com.example.cloudruid.model.entity.Order;
import com.example.cloudruid.model.service.order.OrderAddServiceModel;

import java.util.Set;

public interface OrderService {

    void addOrder(OrderAddServiceModel orderAddServiceModel);

    Set<Order> getAllOrders();
}
