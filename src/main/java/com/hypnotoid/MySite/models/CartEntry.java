package com.hypnotoid.MySite.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartEntry {

    // private int id;
    @Min(1)
    private int product_id = 0;
    @NotNull
    private String product_name = null;

    private int productAmount=0;

    @Positive
    private double product_price = 0;
    @Positive
    private double total_price = 0;
    @Min(1)
    private int amount;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public void setProduct_price(double product_price) {
        if (product_price > 0) this.product_price = product_price;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price() {
        total_price = product_price * amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
