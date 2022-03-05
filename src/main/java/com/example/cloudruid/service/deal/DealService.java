package com.example.cloudruid.service.deal;

import com.example.cloudruid.model.entity.Deal;
import com.example.cloudruid.model.service.deal.DealAddServiceModel;

import java.util.List;

public interface DealService {

    void addDeal(DealAddServiceModel dealAddServiceModel);

    List<Deal> getAllDeals();

    Deal findDealById(Long id);

//    Deal updateDeal();

    void initializeDeals();
}
