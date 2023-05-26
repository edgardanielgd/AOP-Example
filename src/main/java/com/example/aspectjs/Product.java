package com.example.aspectjs;

public class Product {
    public String name;
    public int unitPrice;
    public int quantity;

    public Product(
            String name,
            int unitPrice,
            int quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
}
