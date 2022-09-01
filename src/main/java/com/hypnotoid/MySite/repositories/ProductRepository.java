package com.hypnotoid.MySite.repositories;

import com.hypnotoid.MySite.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//PagingAndSortingRepository extends CRUDRepository and provides additional methods to ease paginated access to entities:
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    @Query(value = "SELECT * from mysite.items order by orders desc limit 10", nativeQuery = true)
    List<Product> getTop10Orders();


    List<Product> findProductsByNameContainsIgnoreCase(String name);
    List<Product> findProductsByAmount(int amount);
    List<Product> findProductsByPrice(double price);


    List<Product> findAll();
}
//Page<User> users = repository.findAll(PageRequest.of(1, 20));
//create table items (id integer not null auto_increment, amount integer default 0 not null, name varchar(45) not null, orders integer default 0 not null, price double precision not null, primary key (id)) engine=InnoDB
