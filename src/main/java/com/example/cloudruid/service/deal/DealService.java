package com.example.cloudruid.service.deal;

import com.example.cloudruid.model.entity.Deal;
import com.example.cloudruid.model.service.deal.DealAddServiceModel;

import java.util.List;

public interface DealService {

    List<Deal> getAllDeals();

    Deal findDealByName(String name);

    void initializeDeals();
}
