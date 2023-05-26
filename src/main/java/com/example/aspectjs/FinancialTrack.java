package com.example.aspectjs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;

import java.util.ArrayList;

@Aspect
public class FinancialTrack {

    ArrayList<Receipt> receipts = new ArrayList<>();
    int balance = 100;

    @Pointcut("execution(* Store.sellProduct(..))")
    public void processOutcomingProducts() {
        // Incoming money
    }

    @Pointcut("execution(* Store.buyProduct(..))")
    public void processIncomingProducts() {
        // Outgoing money
    }

    // Check if we have money for buying products
    @Around("processIncomingProducts()")
    public void processResupply(ProceedingJoinPoint jp) {

        // Check if our balance is enough
        Object[] args = jp.getArgs();
        String productName = (String) args[0];
        int quantity = (int) args[1];

        // Get caller
        Store store = (Store) jp.getTarget();

        // Get product
        Product product = store.products.get(productName);

        // Check if product does exist
        if (product == null) {
            System.out.println("[Financiero] Producto no encontrado");
            return;
        }

        int total = product.unitPrice * quantity;

        // Check if we have enough money
        if (this.balance < total) {
            System.out.println("[Financiero] No hay suficiente dinero para la compra");
            return;
        }

        Receipt receipt = new Receipt(Receipt.OUTFLOW, total);
        receipts.add(receipt);

        // And finally proceed with the original method
        try {
            jp.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        // Update balance
        this.balance -= total;
    }

    // Update balance after selling products
    @AfterReturning(pointcut = "processOutcomingProducts()", returning = "retVal")
    public void processSell(JoinPoint jp, boolean retVal) {
        // Check if last operation was successfull
        if (!retVal) {
            return;
        }

        // Check if our balance is enough
        Object[] args = jp.getArgs();
        String productName = (String) args[0];
        int quantity = (int) args[1];

        // Get caller
        Store store = (Store) jp.getTarget();

        // Get product
        Product product = store.products.get(productName);

        // Check if product does exist
        if (product == null) {
            System.out.println("[Financiero] Producto no encontrado");
            return;
        }

        int total = (product.unitPrice * quantity);

        Receipt receipt = new Receipt(Receipt.INFLOW, total);
        receipts.add(receipt);

        // Update balance
        this.balance += total;
    }

}
