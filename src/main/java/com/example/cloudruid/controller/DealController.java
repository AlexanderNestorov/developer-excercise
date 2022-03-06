package com.example.cloudruid.controller;

import com.example.cloudruid.model.entity.Deal;
import com.example.cloudruid.model.request.deal.DealAddRequest;
import com.example.cloudruid.model.request.order.OrderAddRequest;
import com.example.cloudruid.model.response.MessageResponse;
import com.example.cloudruid.model.service.deal.DealAddServiceModel;
import com.example.cloudruid.service.deal.DealService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/deal")
public class DealController {

    private final DealService dealService;
    private final ModelMapper modelMapper;

    public DealController(DealService dealService, ModelMapper modelMapper) {
        this.dealService = dealService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDeals() {
        List<Deal> deals = this.dealService.getAllDeals();
        return ResponseEntity.ok(deals);
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> addDeal(@Valid @RequestBody DealAddRequest dealAddRequest,
//                                      BindingResult bindingResult) {
//
//        if(bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Invalid deal request data!"));
//        }
//        System.out.println(dealAddRequest.getProducts());
//
//        DealAddServiceModel dealAddServiceModel =
//                modelMapper.map(dealAddRequest, DealAddServiceModel.class);
//
//
//        dealService.addDeal(dealAddServiceModel);
//
//
//        return ResponseEntity.ok(new MessageResponse("Deal added successfully!"));
//    }
}
