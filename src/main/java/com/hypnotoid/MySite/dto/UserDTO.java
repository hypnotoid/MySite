package com.hypnotoid.MySite.dto;

import com.hypnotoid.MySite.models.CartEntry;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private int id;
    private String username;
    private String password;

    private String firstname;

    private String lastname;
    private boolean enabled = true;
    private Set<CartEntry> cart = new HashSet<>();
    private boolean isEditor = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public Set<CartEntry> getCart() {
        return cart;
    }

    public void setCart(Set<CartEntry> cart) {
        this.cart = cart;
    }

    public boolean isRoleEditor() {
        return isEditor;
    }

    public void setRoleEditor(boolean editor) {
        isEditor = editor;
    }

}
