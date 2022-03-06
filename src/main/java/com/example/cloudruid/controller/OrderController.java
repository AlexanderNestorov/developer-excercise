package com.example.cloudruid.controller;

import com.example.cloudruid.model.entity.Order;
import com.example.cloudruid.model.request.order.OrderAddRequest;
import com.example.cloudruid.model.response.MessageResponse;
import com.example.cloudruid.model.service.order.OrderAddServiceModel;
import com.example.cloudruid.service.order.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        Set<Order> orders = this.orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderAddRequest orderAddRequest,
                                      BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid order request data!"));
        }

        OrderAddServiceModel orderAddServiceModel =
                modelMapper.map(orderAddRequest, OrderAddServiceModel.class);


        orderService.addOrder(orderAddServiceModel);


        return ResponseEntity.ok(new MessageResponse("Order added successfully!"));
    }
}
