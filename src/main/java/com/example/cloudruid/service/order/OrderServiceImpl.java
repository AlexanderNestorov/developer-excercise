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

        boolean hasFirst = orderAddServiceModel.getDiscounts().contains("3for2");
        boolean hasSecond = orderAddServiceModel.getDiscounts().contains("1AndHalf");

        if (hasFirst) {
            firstTarget  = this.dealRepository.findDealByName("3for2")
                    .orElseThrow(() -> new ItemNotFoundException("Deal with name 3for2 not found!"))
                    .getProducts()
                    .stream().map(Product::getName).collect(Collectors.toList());

        }

        if (hasSecond) {
            secondTarget = this.dealRepository.findDealByName("1AndHalf")
                    .orElseThrow(() -> new ItemNotFoundException("Deal with name 1AndHalf not found!"))
                    .getProducts().get(0).getName();
        }

        List<String> fruits = orderAddServiceModel.getProducts();

        List<String> productsForFirstPromotion = new ArrayList<>();
        List<String> productsForSecondPromotion = new ArrayList<>();

        int occurrences = 0;

        while (fruits.size() > 0) {
            System.out.println(fruits + " Start");

            if (fruits.size() >= 3 && hasFirst) {
                for (int i = 0; i < fruits.size(); i++) {
                    if (firstTarget.contains(fruits.get(i))) {
                        productsForFirstPromotion.add(fruits.get(i));
                        occurrences++;
                        if (occurrences == 3) {
                            for (String product : productsForFirstPromotion) {
                                fruits.remove(product);
                            }

                            System.out.println("3 for 2 applied!"  + productsForFirstPromotion);
                            System.out.println(fruits);
                            for (String product : productsForFirstPromotion) {
                                 totalPrice = totalPrice.add(this.productRepository
                                .findProductByName(product)
                                .orElseThrow(
                                        () -> new ItemNotFoundException("Product with name " + product + " not found!"))
                                         .getPrice());
                             }
                            totalPrice = totalPrice.subtract(Collections.min(defineProducts(productsForFirstPromotion), Comparator.comparing(Product::getPrice)).getPrice());
                            productsForFirstPromotion.clear();
                            occurrences = 0;
                            break;
                        }
                    }
                }
            }
            if (fruits.size() >= 2 && hasSecond) {
                if (Collections.frequency(fruits, secondTarget) >= 2) {
                    productsForSecondPromotion.add(fruits.remove(fruits.indexOf(secondTarget)));
                    productsForSecondPromotion.add(fruits.remove(fruits.indexOf(secondTarget)));
                    System.out.println("1 and half applied!" + productsForSecondPromotion);
                    System.out.println(fruits);
                    totalPrice = totalPrice
                        .add((defineProducts(productsForSecondPromotion).get(0).getPrice()).multiply(BigDecimal.valueOf(1.5)));
                    productsForSecondPromotion.clear();

                } else {
                    for (String product : fruits) {
                        totalPrice = totalPrice.add(this.productRepository
                                .findProductByName(product)
                                .orElseThrow(
                                        () -> new ItemNotFoundException("Product with name " + product + " not found!")
                                ).getPrice());
                    }
                    fruits.clear();
                }
            } else {
                for (String product : fruits) {
                    totalPrice = totalPrice.add(this.productRepository
                    .findProductByName(product)
                    .orElseThrow(
                            () -> new ItemNotFoundException("Product with name " + product + " not found!")
                    ).getPrice());
                }
                fruits.clear();
            }
            System.out.println(fruits + " End");
        }
        System.out.println(totalPrice);
    }

    @Override
    public Set<Order> getAllOrders() {
        return new LinkedHashSet<>(this.orderRepository.findAll());
    }

    private List<Product> defineProducts(List<String> products) {
        return products.stream().map(product -> this.productRepository.findProductByName(product)
                .orElseThrow(() -> new ItemNotFoundException("Product with name " + product + " not found!")))
                .collect(Collectors.toList());
    }
}
