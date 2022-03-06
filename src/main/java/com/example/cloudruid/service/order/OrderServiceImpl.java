package com.example.cloudruid.service.order;

import com.example.cloudruid.model.entity.Order;
import com.example.cloudruid.model.entity.Product;
import com.example.cloudruid.model.exception.ItemNotFoundException;
import com.example.cloudruid.model.service.order.OrderAddServiceModel;
import com.example.cloudruid.repository.DealRepository;
import com.example.cloudruid.repository.OrderRepository;
import com.example.cloudruid.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DealRepository dealRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, DealRepository dealRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.dealRepository = dealRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addOrder(OrderAddServiceModel orderAddServiceModel) {

        BigDecimal totalPrice = BigDecimal.valueOf(0);

        List<String> firstTarget = new ArrayList<>();
        String secondTarget = null;

        if (orderAddServiceModel.getDiscounts().contains("3for2")) {
            firstTarget  = this.dealRepository.findDealByName("3for2")
                    .orElseThrow(() -> new ItemNotFoundException("Deal with name 3for2 not found!"))
                    .getProducts()
                    .stream().map(Product::getName).collect(Collectors.toList());

        }

        if (orderAddServiceModel.getDiscounts().contains("1AndHalf")) {
            secondTarget = this.dealRepository.findDealByName("1AndHalf")
                    .orElseThrow(() -> new ItemNotFoundException("Deal with name 1AndHalf not found!"))
                    .getProducts().get(0).getName();
        }

        List<String> fruits = orderAddServiceModel.getProducts();

        List<String> productsForFirstPromotion = new ArrayList<>();
        List<String> productsForSecondPromotion = new ArrayList<>();

        int occurrences = 0;
        boolean firstApplied = false;
        boolean secondApplied = false;

        while (fruits.size() > 2 && !firstApplied && !secondApplied) {

            for (int i = 0; i < fruits.size(); i++) {
                if (firstTarget.contains(fruits.get(i))) {
                    if (occurrences == 3) {
                        occurrences = 0;
                        break;
                    }
                    productsForFirstPromotion.add(fruits.get(i));
                    occurrences++;
                    if (occurrences == 3) {
                        fruits.removeAll(productsForFirstPromotion);
                        firstApplied = true;
                    }
                }
            }

            if (Collections.frequency(fruits, secondTarget) >= 2) {
                productsForSecondPromotion.add(fruits.remove(fruits.indexOf(secondTarget)));
                productsForSecondPromotion.add(fruits.remove(fruits.indexOf(secondTarget)));
                secondApplied = true;
            }

            System.out.println(secondApplied);
            System.out.println(firstApplied);

        }
        //Sum the first promotion
        if (firstApplied) {
            for (String product : productsForFirstPromotion) {
                totalPrice = totalPrice.add(this.productRepository
                        .findProductByName(product)
                        .orElseThrow(
                                () -> new ItemNotFoundException("Product with name " + product + " not found!")
                        ).getPrice());
            }
            totalPrice = totalPrice.subtract(Collections.min(defineProducts(productsForFirstPromotion),
                    Comparator.comparing(Product::getPrice)).getPrice());
        }
        //Sum the second promotion
        if (secondApplied) {
            totalPrice = totalPrice
                    .add((defineProducts(productsForSecondPromotion).get(0).getPrice()).multiply(BigDecimal.valueOf(1.5)));
        }

        //Sum the rest of the products
        for (String product : fruits) {
            totalPrice = totalPrice.add(this.productRepository
                    .findProductByName(product)
                    .orElseThrow(
                            () -> new ItemNotFoundException("Product with name " + product + " not found!")
                    ).getPrice());
        }
        //Clear the till
        fruits.clear();

        System.out.println(totalPrice);


        System.out.println(productsForFirstPromotion);
        System.out.println(productsForSecondPromotion);
        System.out.println(fruits);
    }

    @Override
    public Set<Order> getAllOrders() {
        return new LinkedHashSet<>(this.orderRepository.findAll());
    }

    private List<Product> defineProducts(List<String> products) {
        return products.stream().map(p -> this.productRepository.findProductByName(p)
                .orElseThrow(() -> new ItemNotFoundException("Product with name " + p + " not found!")))
                .collect(Collectors.toList());
    }
}
