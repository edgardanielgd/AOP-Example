package com.example.aspectjs;

public class Receipt {

    public static String OUTFLOW = "Salida";
    public static String INFLOW = "Entrada";

    public String flow;

    public int total;

    public Receipt(
            String flow,
            int total) {
        this.flow = flow;
        this.total = total;
    }
}
