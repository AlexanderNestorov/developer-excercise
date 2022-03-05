package com.example.cloudruid.service.deal;

import com.example.cloudruid.model.entity.Deal;
import com.example.cloudruid.model.entity.Product;
import com.example.cloudruid.model.exception.ItemNotFoundException;
import com.example.cloudruid.model.service.deal.DealAddServiceModel;
import com.example.cloudruid.repository.DealRepository;
import com.example.cloudruid.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealServiceImpl implements DealService{

    private final DealRepository dealRepository;
    private final ProductRepository productRepository;

    public DealServiceImpl(DealRepository dealRepository, ProductRepository productRepository) {
        this.dealRepository = dealRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addDeal(DealAddServiceModel dealAddServiceModel) {
        createDeal(dealAddServiceModel.getName(), defineProducts(dealAddServiceModel.getProducts()));
    }

    @Override
    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal findDealById(Long id) {
        return dealRepository.findDealById(id)
                .orElseThrow(() -> new ItemNotFoundException("Deal not found!"));
    }

    @Override
    public void initializeDeals() {
        if(dealRepository.count() == 0) {
            createDeal("Buy 3 Items, Get the Third One Free",
                    defineProducts(List.of("Apple", "Banana","Potato")));

            createDeal("Buy 2 Items, Get the Second One For Half Price",
                    defineProducts(List.of("Potato")));
        }
    }

    private void createDeal(String name, List<Product> products) {
        Deal deal = new Deal();

        deal.setName(name);
        deal.setProducts(products);

        this.dealRepository.save(deal);
    }

    private List<Product> defineProducts(List<String> products) {
        List<Product> productEntities = new ArrayList<>();
        for (String productName : products) {
            Product product = this.productRepository.findProductByName(productName)
                    .orElseThrow(() -> new ItemNotFoundException("Product not found!"));
            productEntities.add(product);
        }
        return productEntities;
    }
}
