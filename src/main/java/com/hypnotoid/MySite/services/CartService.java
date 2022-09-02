package com.hypnotoid.MySite.services;

import com.hypnotoid.MySite.models.CartEntry;
import com.hypnotoid.MySite.models.Product;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.Set;

@Service
public class CartService {

    private final ProductService productService;

    private final OrderDTOService orderService;


    public void deleteItem(final int product_id, final Set<CartEntry> cart) {
        if (cart == null) return;

        for (Iterator<CartEntry> iter = cart.iterator(); iter.hasNext(); ) {
            CartEntry entry = iter.next();
            if (entry.getProduct_id() == product_id) iter.remove();
        }
    }

    public void setAmount(final int product_id, final int amount, final Set<CartEntry> cart) {
        if (cart == null || amount < 0) return;
        for (Iterator<CartEntry> iter = cart.iterator(); iter.hasNext(); ) {
            CartEntry entry = iter.next();
            if (entry.getProduct_id() == product_id) {
                Product product = productService.getById(product_id);
                if (amount > 0 && product != null) {
                    if (product.getAmount() >= amount) {
                        entry.setAmount(amount);
                        entry.setTotal_price();
                    }
                }
                else cart.remove(entry);
            }
        }
    }

    public void addItems(final int product_id, final int amount, final Set<CartEntry> cart) {
        if (cart == null || amount <= 0) return;
        for (Iterator<CartEntry> iter = cart.iterator(); iter.hasNext(); ) {
            CartEntry entry = iter.next();
            if (entry.getProduct_id() == product_id) {
                Product product = productService.getById(product_id);
                if (product != null) {
                    if (product.getAmount() < amount + entry.getAmount()) return;
                    if (product.getAmount() >= amount + entry.getAmount()) {
                        entry.setProductAmount(product.getAmount());
                        entry.setAmount(amount + entry.getAmount());
                        entry.setProduct_price(product.getPrice());
                        entry.setProduct_name(product.getName());
                        entry.setTotal_price();
                        return;
                    }
                }
            }
        }
        Product product = productService.getById(product_id);
        if (product != null && product.getAmount() >= amount) {
            CartEntry entry = new CartEntry();
            entry.setProduct_id(product_id);
            entry.setAmount(amount);
            entry.setProduct_price(product.getPrice());
            entry.setProduct_name(product.getName());
            entry.setProductAmount(product.getAmount());
            entry.setTotal_price();
            cart.add(entry);
        }
    }

    public double getTotalPrice(Set<CartEntry> cart) {
        if (cart == null) return 0;
        double out = 0;
        for (CartEntry entry : cart) {
            out += entry.getTotal_price();
        }
        return out;
    }

    public void order(@Valid final Set<CartEntry> cart, int userId) {
        if (cart == null) return;
        for (Iterator<CartEntry> iter = cart.iterator(); iter.hasNext(); ) {
            CartEntry entry = iter.next();
            productService.order(entry.getProduct_id(), entry.getAmount());
            orderService.add(entry.getProduct_id(),userId,entry.getAmount());
            iter.remove();
        }
    }

    public CartService(ProductService productService, OrderDTOService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }
}
