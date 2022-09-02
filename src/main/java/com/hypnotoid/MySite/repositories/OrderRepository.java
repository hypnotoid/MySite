package com.hypnotoid.MySite.repositories;

import com.hypnotoid.MySite.models.Order;
import com.hypnotoid.MySite.models.Product;
import com.hypnotoid.MySite.models.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional //todo ugly fix
public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findAll();

    void deleteAllByUser(User user);

    void deleteAllByProduct(Product product);

    boolean existsByProduct(Product product);
}
