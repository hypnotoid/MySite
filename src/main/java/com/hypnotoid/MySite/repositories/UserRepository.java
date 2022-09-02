package com.hypnotoid.MySite.repositories;


import com.hypnotoid.MySite.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


    User findByUsername(String login);


    Set<User> findUsersByUsernameContainsIgnoreCase(String name);

    Set<User> findUsersByFirstnameContainsIgnoreCase(String name);

    Set<User> findUsersByLastnameContainsIgnoreCase(String name);

    List<User> findAll();

    boolean existsUserByUsername(String login);

    boolean existsUserById(int id);

}
