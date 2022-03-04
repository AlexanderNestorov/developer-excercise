package com.example.cloudruid.service.deal;

import com.example.cloudruid.model.entity.Deal;
import com.example.cloudruid.model.entity.Product;
import com.example.cloudruid.repository.DealRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DealServiceImpl implements DealService{

    private final DealRepository dealRepository;

    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public void initializeDeals() {
        if(dealRepository.count() == 0) {
            List<Deal> deals = new ArrayList<>();

            Deal twoForThePriceOfThree = new Deal();

            twoForThePriceOfThree
                    .setName("Buy 3 Items, Pay Only 2!");

            deals.add(twoForThePriceOfThree);

            Deal oneSecondHalfPrice = new Deal();

            oneSecondHalfPrice
                    .setName("Buy 1 Item, Get the Second One For Half!");

            deals.add(oneSecondHalfPrice);

            this.dealRepository.saveAll(deals);
        }
    }
}
