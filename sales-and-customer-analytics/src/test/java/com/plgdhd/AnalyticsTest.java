package com.plgdhd;

import com.plgdhd.Analytics;
import com.plgdhd.Category;
import com.plgdhd.Customer;
import com.plgdhd.Order;
import com.plgdhd.OrderItem;
import com.plgdhd.OrderStatus;
import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyticsTest {

    private List<Order> orders;
    private Customer customer1;
    private Customer customer2;
    private OrderItem item1;
    private OrderItem item2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer("C1", "Samsung Rep", "samsung@tech.com", LocalDateTime.now(), 30, "Seoul");
        if (customer1.getCustomerId() == null || customer1.getCustomerId().isEmpty()) {
            throw new IllegalArgumentException("Идентификатор клиента не может быть пустым");
        }
        if (customer1.getName() == null || customer1.getName().isEmpty()) {
            throw new IllegalArgumentException("Имя клиента не может быть пустым");
        }
        if (customer1.getEmail() == null || !customer1.getEmail().contains("@")) {
            throw new IllegalArgumentException("Электронная почта должна быть валидной");
        }
        if (customer1.getAge() <= 0) {
            throw new IllegalArgumentException("Возраст клиента должен быть положительным");
        }
        if (customer1.getCity() == null || customer1.getCity().isEmpty()) {
            throw new IllegalArgumentException("Город клиента не может быть пустым");
        }

        customer2 = new Customer("C2", "Apple Rep", "apple@tech.com", LocalDateTime.now(), 25, "Cupertino");
        if (customer2.getCustomerId() == null || customer2.getCustomerId().isEmpty()) {
            throw new IllegalArgumentException("Идентификатор клиента не может быть пустым");
        }
        if (customer2.getName() == null || customer2.getName().isEmpty()) {
            throw new IllegalArgumentException("Имя клиента не может быть пустым");
        }
        if (customer2.getEmail() == null || !customer2.getEmail().contains("@")) {
            throw new IllegalArgumentException("Электронная почта должна быть валидной");
        }
        if (customer2.getAge() <= 0) {
            throw new IllegalArgumentException("Возраст клиента должен быть положительным");
        }
        if (customer2.getCity() == null || customer2.getCity().isEmpty()) {
            throw new IllegalArgumentException("Город клиента не может быть пустым");
        }

        item1 = new OrderItem("Samsung Galaxy", 2, 1000.0, Category.ELECTRONICS);
        if (item1.getProductName() == null || item1.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        if (item1.getQuantity() <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        if (item1.getPrice() < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
        if (item1.getCategory() == null) {
            throw new IllegalArgumentException("Категория не может быть пустой");
        }

        item2 = new OrderItem("Apple iPhone", 3, 500.0, Category.ELECTRONICS);
        if (item2.getProductName() == null || item2.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        if (item2.getQuantity() <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        if (item2.getPrice() < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
        if (item2.getCategory() == null) {
            throw new IllegalArgumentException("Категория не может быть пустой");
        }

        orders = new ArrayList<>();
        Order order1 = new Order("O1", LocalDateTime.now(), customer1, Arrays.asList(item1), OrderStatus.DELIVERED);
        Order order2 = new Order("O2", LocalDateTime.now(), customer2, Arrays.asList(item1, item2),
                OrderStatus.DELIVERED);
        Order order3 = new Order("O3", LocalDateTime.now(), customer1, Arrays.asList(item2), OrderStatus.PROCESSING);
        if (order1.getOrderId() == null || order1.getOrderId().isEmpty()) {
            throw new IllegalArgumentException("Идентификатор заказа не может быть пустым");
        }
        if (order2.getOrderId() == null || order2.getOrderId().isEmpty()) {
            throw new IllegalArgumentException("Идентификатор заказа не может быть пустым");
        }
        if (order3.getOrderId() == null || order3.getOrderId().isEmpty()) {
            throw new IllegalArgumentException("Идентификатор заказа не может быть пустым");
        }
        if (order1.getCustomer() == null) {
            throw new IllegalArgumentException("Клиент заказа не может быть пустым");
        }
        if (order1.getItems() == null || order1.getItems().isEmpty()) {
            throw new IllegalArgumentException("Список товаров не может быть пустым");
        }
        if (order1.getStatus() == null) {
            throw new IllegalArgumentException("Статус заказа не может быть пустым");
        }
        orders.addAll(Arrays.asList(order1, order2, order3));
    }

    @Test
    void testUniqueCities() {
        Set<String> cities = Analytics.uniqueCities(orders);
        assertEquals(2, cities.size(), "Должен быть ровно 2 уникальных города");
        assertTrue(cities.contains("Seoul"), "Должен содержать Seoul");
        assertTrue(cities.contains("Cupertino"), "Должен содержать Cupertino");
    }

    @Test
    void testUniqueCitiesEmptyList() {
        Set<String> cities = Analytics.uniqueCities(new ArrayList<>());
        assertTrue(cities.isEmpty(), "Должен возвращать пустой набор для пустого списка");
    }

    @Test
    void testTotalIncome() {
        double total = Analytics.totalIncome(orders);
        assertEquals(5500.0, total, 0.001, "Общий доход должен быть 5500.0");
    }

    @Test
    void testTotalIncomeNoDeliveredOrders() {
        List<Order> pendingOrders = List.of(
                new Order("O4", LocalDateTime.now(), customer1, Arrays.asList(item1), OrderStatus.PROCESSING));
        double total = Analytics.totalIncome(pendingOrders);
        assertEquals(0.0, total, 0.001, "Общий доход должен быть 0 для не доставленных заказов");
    }

    @Test
    void testMostPopularProduct() {
        String product = Analytics.mosPopularProduct(orders);
        assertEquals("Apple iPhone", product, "iPhone должен быть самым популярным продуктом");
    }

    @Test
    void testMostPopularProductEmptyList() {
        String product = Analytics.mosPopularProduct(new ArrayList<>());
        assertNull(product, "Должен возвращать null для пустого списка");
    }

    @Test
    void testAverageCheck() {
        double average = Analytics.averageCheck(orders);
        assertEquals(2750.0, average, 0.001, "Средний чек должен быть 2750.0");
    }

    @Test
    void testAverageCheckNoDeliveredOrders() {
        List<Order> pendingOrders = List.of(
                new Order("O4", LocalDateTime.now(), customer1, Arrays.asList(item1), OrderStatus.PROCESSING));
        double average = Analytics.averageCheck(pendingOrders);
        assertEquals(0.0, average, 0.001, "Средний чек должен быть 0 для не доставленных заказов");
    }

    @Test
    void testFrequentCustomers() {
        List<Order> frequentOrders = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            frequentOrders.add(
                    new Order("O" + (i + 5), LocalDateTime.now(), customer1, Arrays.asList(item1),
                            OrderStatus.DELIVERED));
        }
        frequentOrders.add(
                new Order("O11", LocalDateTime.now(), customer2, Arrays.asList(item2), OrderStatus.DELIVERED));

        List<Customer> frequent = Analytics.frequentCustomers(frequentOrders);
        assertEquals(1, frequent.size(), "Должен возвращать 1 частого клиента");
        assertEquals(customer1, frequent.get(0), "Samsung Rep должен быть частым клиентом");
    }

    @Test
    void testFrequentCustomersNoFrequent() {
        List<Customer> frequent = Analytics.frequentCustomers(orders);
        assertTrue(frequent.isEmpty(),
                "Должен возвращать пустой список, когда ни один клиент не сделал более 5 заказов");
    }
}