package com.hypnotoid.MySite.models;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int id = 0;

    @Column(name = "name", length = 45, nullable = false)
    private String name = "";


    @Column(name = "price", nullable = false)
    private double price = 0;

    @Column(name = "amount", nullable = false)
    private int amount = 0;

    @Column(name = "orders", nullable = false)
    private int orders = 0;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 0) this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount >= 0) this.amount = amount;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        if (orders >= 0) this.orders = orders;
    }
}
