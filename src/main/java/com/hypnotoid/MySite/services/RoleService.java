package com.hypnotoid.MySite.services;

import com.hypnotoid.MySite.Roles;
import com.hypnotoid.MySite.models.Role;
import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService { //Role DAO
    private final RoleRepository roleRepository;
    public Role get(Roles roles){
        for (Role role: roleRepository.findAll()) {
            if (role.getId() == roles.getId()) return role;
        }
        return null;
    }
    public boolean isEditor(User user) {
        if (user == null) return false;
        for (Role role : user.getRoles()) {
            if (role.getId() == 3) return true;
        }
        return false;
    }
    public boolean isAdmin(User user) {
        if (user == null) return false;
        for (Role role : user.getRoles()) {
            if (role.getId() == 1) return true;
        }
        return false;
    }


    public Set<Role> set(boolean isAdmin, boolean isEditor, boolean isUser){
        Set<Role> roles = new HashSet<>();
        if (isAdmin) roles.add(get(Roles.ADMIN));
        if (isEditor) roles.add(get(Roles.EDITOR));
        if (isUser) roles.add(get(Roles.USER));
        return roles;
    }
    //@Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
