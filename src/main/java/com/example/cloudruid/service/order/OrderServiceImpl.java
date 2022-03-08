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

        Order order = new Order();

        order
                .setProducts(defineProducts(orderAddServiceModel.getProducts()))
                .setTotal(calculateTotalPrice(orderAddServiceModel));


        this.orderRepository.save(order);
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

    //Main method of the App responsible for calculating the price based on discounts
    private BigDecimal calculateTotalPrice(OrderAddServiceModel orderAddServiceModel) {
        //Check which deals should be applied
        boolean hasFirst = orderAddServiceModel.getDiscounts().contains("3for2");
        boolean hasSecond = orderAddServiceModel.getDiscounts().contains("1AndHalf");

        //Initialize the total price
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        //Initialize the products which are applicable for the discounts
        List<String> firstTarget = new ArrayList<>();
        String secondTarget = null;

        //If the 3 for 2 discount is applied, set the products which are applicable for the discount
        if (hasFirst) {
            firstTarget  = this.dealRepository.findDealByName("3for2")
                    .orElseThrow(() -> new ItemNotFoundException("Deal with name 3for2 not found!"))
                    .getProducts()
                    .stream().map(Product::getName).collect(Collectors.toList());

        }

        //If the 1 and Half discount is applied, set the products which are applicable for the discount
        if (hasSecond) {
            secondTarget = this.dealRepository.findDealByName("1AndHalf")
                    .orElseThrow(() -> new ItemNotFoundException("Deal with name 1AndHalf not found!"))
                    .getProducts().get(0).getName();
        }

        //The till of products which have to be calculated
        List<String> fruits = orderAddServiceModel.getProducts();

        //Temporary lists for holding which have already been selected for the discounts
        List<String> productsForFirstPromotion = new ArrayList<>();
        List<String> productsForSecondPromotion = new ArrayList<>();

        //Variable helping with the first discount
        int occurrences = 0;

        //While there are still products to be calculated
        while (fruits.size() > 0) {
            System.out.println(fruits + " Start");

            //If the 3 for 2 discount is applied, check if there are more than 2 products and do the discount
            if (fruits.size() >= 3 && hasFirst) {
                for (int i = 0; i < fruits.size(); i++) {
                    //for each fruit check if it is in the list of products which are applicable for the discount
                    if (firstTarget.contains(fruits.get(i))) {
                        productsForFirstPromotion.add(fruits.get(i));
                        occurrences++;
                        //If 3 fruits have been selected, remove them from the list
                        if (occurrences == 3) {
                            for (String product : productsForFirstPromotion) {
                                fruits.remove(product);
                            }
//                            System.out.println("3 for 2 applied!"  + productsForFirstPromotion);
//                            System.out.println(fruits);
                            //Sum the price of the products which have been selected
                            for (String product : productsForFirstPromotion) {
                                totalPrice = totalPrice.add(this.productRepository
                                        .findProductByName(product)
                                        .orElseThrow(
                                                () -> new ItemNotFoundException("Product with name " + product + " not found!"))
                                        .getPrice());
                            }
                            //Apply the discount
                            totalPrice = totalPrice.subtract(Collections.min(defineProducts(productsForFirstPromotion), Comparator.comparing(Product::getPrice)).getPrice());
                            //Reset the variables
                            productsForFirstPromotion.clear();
                            occurrences = 0;
                            break;
                        }
                    }
                }
            }
            //If the 1 and Half discount is applied, check if there are more than 1 product and do the discount
            if (fruits.size() >= 2 && hasSecond) {
                //if there are 2 or more same products,remove them
                if (Collections.frequency(fruits, secondTarget) >= 2) {
                    productsForSecondPromotion.add(fruits.remove(fruits.indexOf(secondTarget)));
                    productsForSecondPromotion.add(fruits.remove(fruits.indexOf(secondTarget)));
//                    System.out.println("1 and half applied!" + productsForSecondPromotion);
//                    System.out.println(fruits);
                    //Apply the discount
                    totalPrice = totalPrice
                            .add((defineProducts(productsForSecondPromotion).get(0).getPrice()).multiply(BigDecimal.valueOf(1.5)));
                    productsForSecondPromotion.clear();
                    //If there are no same products, sum them
                } else {
                    for (String product : fruits) {
                        totalPrice = totalPrice.add(this.productRepository
                                .findProductByName(product)
                                .orElseThrow(
                                        () -> new ItemNotFoundException("Product with name " + product + " not found!")
                                ).getPrice());
                    }
                    //if we have summed everything, remove the products from the list
                    fruits.clear();
                }
                //If no discount is applied, sum the products
            } else {
                for (String product : fruits) {
                    totalPrice = totalPrice.add(this.productRepository
                            .findProductByName(product)
                            .orElseThrow(
                                    () -> new ItemNotFoundException("Product with name " + product + " not found!")
                            ).getPrice());
                }
                //if we have summed everything, remove the products from the list
                fruits.clear();
            }
        }
        //return the total price
        return totalPrice;
    }
}
