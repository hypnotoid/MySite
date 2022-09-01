package com.hypnotoid.MySite.repositories;

import com.hypnotoid.MySite.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role getRoleById(int id);
    Role getRoleByName(String name);

    Set<Role> findAll();
}
