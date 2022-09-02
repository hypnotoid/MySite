package com.hypnotoid.MySite.services;


import com.hypnotoid.MySite.models.Product;
import com.hypnotoid.MySite.repositories.OrderRepository;
import com.hypnotoid.MySite.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class ProductService {

    private final ProductRepository productRepository;


    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public void add20RandomProducts() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            String name = "Product" + random.nextInt() % 500 + 501;
            Product product = new Product();
            product.setName(name);
            product.setPrice(roundToTwo(random.nextDouble()));
            product.setAmount(random.nextInt() % 50 + 51);
            product.setOrders(random.nextInt() % 100 + 101);
            productRepository.save(product);
        }
    }

    private double roundToTwo(final double input) {
        return Math.round(input * 10000) / 100.;
    }

    public List<Product> getTop10() {
        return productRepository.getTop10Orders();
    }

    public List<Product> removeAmount0(List<Product> products) {
        if (products == null) return null;
        List<Product> out = new ArrayList<>();
        for (Product product : products) {
            if (product.getAmount() > 0) out.add(product);
        }
        return out;
    }

    public int getAmountForID(int id) {
        Product p = getById(id);
        if (p != null) {
            return p.getAmount();
        } else return 0;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(final int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public List<Product> find(String find) {
        List<Product> products = new ArrayList<>();
        if (find != null) {
            int findInteger;
            double findDouble;
            try {
                findInteger = Integer.parseInt(find);
                products.addAll(productRepository.findProductsByAmount(findInteger));
            } catch (NumberFormatException ignored) {
            }
            try {
                findDouble = Double.parseDouble(find);
                products.addAll(productRepository.findProductsByPrice(findDouble));
            } catch (NumberFormatException ignored) {
            }
            products.addAll(productRepository.findProductsByNameContainsIgnoreCase(find));
        }
        return products;
    }

    public boolean existOrdersByProductId(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.filter(orderRepository::existsByProduct).isPresent();
    }

    public void deleteById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            orderRepository.deleteAllByProduct(productOptional.get());
            productRepository.deleteById(id);
        }
    }

    public void add(final Product product) {
        if (product != null) {
            if (!productRepository.existsById(product.getId())) {
                productRepository.save(product);
            }
        }
    }

    public void edit(Product product) {
        if (product != null) {
            if (productRepository.existsById(product.getId())) {
                productRepository.save(product);
            }
        }
    }

    public void order(int product_id, int amount) {
        if (product_id > 0 && amount > 0) {
            Product product = getById(product_id);
            if (product != null) {
                product.setAmount(product.getAmount() - amount);
                product.setOrders(product.getOrders() + amount);
                productRepository.save(product);
            }
        }
    }

    public Product create() {
        return new Product();
    }
}
