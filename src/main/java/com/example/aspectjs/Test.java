package com.example.aspectjs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Store store = (Store) context.getBean("storeBean");
        FinancialTrack financialTrack = (FinancialTrack) context.getBean("financialTrackBean");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bienvenido tinendita");
        System.out.println("Commands: ");
        System.out.println("list");
        System.out.println("balance");
        System.out.println("receipts");
        System.out.println("buy <product> <quantity>");
        System.out.println("sell <product> <quantity>");
        System.out.println("add <product> <unitPrice>");

        String line;
        line = br.readLine();

        while (line != null && !line.equals("exit")) {
            String[] parts = line.split(" ");

            if (parts.length > 0) {
                String command = parts[0];
                String arg1 = parts.length > 1 ? parts[1] : "";
                String arg2 = parts.length > 2 ? parts[2] : "";

                switch (command) {
                    case "list":
                        for (Product p : store.getAllProducts()) {
                            System.out.println(p.name + " " + p.quantity);
                        }
                        break;
                    case "balance":
                        System.out.println("Balance: " + financialTrack.balance);
                        break;
                    case "receipts":
                        for (Receipt r : financialTrack.receipts) {
                            System.out.println(r.flow + " " + r.total);
                        }
                        break;
                    case "buy":
                        if (!arg1.equals("") && !arg2.equals("")) {
                            store.buyProduct(arg1, Integer.parseInt(arg2));
                        }
                        break;
                    case "add":
                        if (!arg1.equals("") && !arg2.equals("")) {
                            store.addProduct(arg1, Integer.parseInt(arg2));
                        }
                        break;
                    case "sell":
                        if (!arg1.equals("") && !arg2.equals("")) {
                            store.sellProduct(arg1, Integer.parseInt(arg2));
                        }
                        break;
                    default:
                        System.out.println("Unknown command");
                        break;
                }
            }
            line = br.readLine();
        }

    }
}
