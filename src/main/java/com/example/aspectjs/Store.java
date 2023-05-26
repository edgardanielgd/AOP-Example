package com.example.aspectjs;

import java.lang.reflect.Array;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

public class Store {

    public HashMap<String, Product> products;

    public Store() {
        this.products = new HashMap<>();
    }

    public ArrayList<Product> getAllProducts() {
        return new ArrayList<>(this.products.values());
    }

    public boolean sellProduct(String productName, int quantity) {

        // Check if we have enough products
        Product product = this.products.get(productName);

        if (product == null) {
            System.out.println("Producto no encontrado");
            return false;
        }

        if (product.quantity < quantity) {
            System.out.println("No hay suficientes productos");
            return false;
        }

        // Update product quantity
        product.quantity -= quantity;

        System.out.println(
                "Producto " + product.name +
                        " vendido, cantidad: " + quantity +
                        " cantidad restante: " + (product.quantity));

        return true;
    }

    public void addProduct(String productName, int unitPrice) {
        if (!this.products.containsKey(productName)) {
            this.products.put(productName, new Product(productName, unitPrice, 0));
        }
    }

    public void buyProduct(String productName, int quantity) {

        Product product = this.products.get(productName);

        if (product == null) {
            System.out.println("Producto no encontrado");
            return;
        }

        // Update product quantity
        product.quantity += quantity;

        System.out.println(
                "Producto " + product.name +
                        " reabastecido, cantidad: " + quantity +
                        " cantidad restante: " + (product.quantity));
    }

}