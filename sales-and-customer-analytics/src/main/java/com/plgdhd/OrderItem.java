package com.plgdhd;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public
class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;
}
