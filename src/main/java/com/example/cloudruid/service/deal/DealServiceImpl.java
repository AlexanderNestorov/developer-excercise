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
        createDeal(dealAddServiceModel.getName(),
                defineProducts(dealAddServiceModel.getProducts()));
    }

    @Override
    public List<Deal> getAllDeals() {
        return this.dealRepository.findAll();
    }

    @Override
    public Deal findDealByName(String name) {
        return this.dealRepository.findDealByName(name)
                .orElseThrow(() -> new ItemNotFoundException("Deal with name " + name + " not found!"));
    }

//    @Override
//    public Deal findDealById(Long id) {
//        return dealRepository.findDealById(id)
//                .orElseThrow(() -> new ItemNotFoundException("Deal not found!"));
//    }

    @Override
    public void initializeDeals() {
        if(this.dealRepository.count() == 0) {
            createDeal("3for2",
                    defineProducts(List.of("Apple", "Banana","Potato")));

            createDeal("1AndHalf",
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
                    .orElseThrow(() -> new ItemNotFoundException("Product with name " + productName + " not found!"));
            productEntities.add(product);
        }
        return productEntities;
    }
}
