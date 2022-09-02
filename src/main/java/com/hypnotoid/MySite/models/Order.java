package com.hypnotoid.MySite.models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id = 0;

    @ManyToOne()
    @JoinTable(name = "orders_users", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user = new User();

    @ManyToOne()
    @JoinTable(name = "orders_products", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Product product;

    private int amount;

    private Date orderDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
