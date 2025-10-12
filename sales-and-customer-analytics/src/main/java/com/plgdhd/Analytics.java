package com.plgdhd;

import java.util.*;
import java.util.stream.Collectors;

public class Analytics {

    public static Set<String> uniqueCities(List<Order> orders) {
        return orders.stream()
                .map(order -> order.getCustomer().getCity())
                .collect(Collectors.toSet());
    }

    public static double totalIncome(List<Order> orders) {
        return orders.stream()
                .filter(e -> e.getStatus() == OrderStatus.DELIVERED)
                .flatMap(e -> e.getItems().stream())
                .mapToDouble(iter -> iter.getPrice() * iter.getQuantity())
                .sum();
    }

    public static String mosPopularProduct(List<Order> orders) {
        return orders.stream()
                .flatMap(el -> el.getItems().stream())
                .collect(Collectors.toMap(
                        OrderItem::getProductName,
                        OrderItem::getQuantity,
                        Integer::sum))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static double averageCheck(List<Order> orders) {
        return orders.stream()
                .filter(el -> el.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(el -> el.getItems().stream()
                        .mapToDouble(it -> it.getPrice() * it.getQuantity())
                        .sum())
                .average()
                .orElse(.0);
    }

    public static List<Customer> frequentCustomers(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)
                .toList();
    }
}
