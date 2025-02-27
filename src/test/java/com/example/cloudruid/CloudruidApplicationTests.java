package com.example.cloudruid;

import com.example.cloudruid.model.service.order.OrderAddServiceModel;
import com.example.cloudruid.repository.DealRepository;
import com.example.cloudruid.repository.ProductRepository;
import com.example.cloudruid.service.order.OrderService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CloudruidApplicationTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private OrderService orderService;


    @Test
    @DisplayName("Test the loading of test subjects")
    void contextLoads() {
        assertThat(productRepository).isNotNull();
        assertThat(dealRepository).isNotNull();
        assertThat(orderService).isNotNull();
    }

    @Test
    @DisplayName("Test if products are initialized correctly")
    void testProductsInitialization() {
        assertThat(productRepository.findAll().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Test if deals are initialized correctly")
    void testDealsInitialization() {
        assertThat(dealRepository.findAll().size()).isEqualTo(2);
    }

}
