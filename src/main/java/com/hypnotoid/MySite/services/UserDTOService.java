package com.hypnotoid.MySite.services;


import com.hypnotoid.MySite.converters.UserDTOConverter;
import com.hypnotoid.MySite.dto.UserDTO;
import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserDTOService { //DAO data access object. returns userDTO
    private final UserRepository userRepo;

    private final UserDTOConverter converter;
    private final RoleService roleService;

    private final OrderDTOService orderDTOService;

    public UserDTOService(UserRepository userRepo, UserDTOConverter converter, RoleService roleService, OrderDTOService orderDTOService) {
        this.userRepo = userRepo;
        this.converter = converter;
        this.roleService = roleService;
        this.orderDTOService = orderDTOService;
    }

    public List<UserDTO> getAll() {
        List<UserDTO> users = new ArrayList<>();
        for (com.hypnotoid.MySite.models.User u : userRepo.findAll()) {
            users.add(converter.fromUserToDTO(u));
        }
        return users;
    }

    public UserDTO getById(final int id) {
        Optional<User> user = userRepo.findById(id);
        return user.map(converter::fromUserToDTO).orElse(null);
    }

    public Set<UserDTO> convert(Set<User> users) {
        if (users == null) return null;
        Set<UserDTO> userDTOS = new HashSet<>();
        for (User user : users) {
            userDTOS.add(converter.fromUserToDTO(user));
        }
        return userDTOS;
    }

    public UserDTO getByLogin(final String login) {
        User user = userRepo.findByUsername(login);
        if (user != null) {
            return converter.fromUserToDTO(user);
        }
        return null;
    }

    public boolean notExistById(final int id) {
        return userRepo.existsUserById(id);
    }

    public boolean existByUsername(final String login) {
        return userRepo.existsUserByUsername(login);
    }

    public void deleteById(final int id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (roleService.isAdmin(user)) return;
            orderDTOService.deleteByUser(user);
            //   orderRepo.deleteAllByUser(user);
            user.setRoles(null);
            userRepo.save(user);
            userRepo.deleteById(id);
        }
    }

    public UserDTO create() {
        return new UserDTO();
    }

    public void edit(UserDTO userDTO) {
        if (userDTO == null) return;
        if (userRepo.existsUserById(userDTO.getId())) {
            userRepo.save(converter.fromDTOToUser(userDTO));
        }
    }

    public Set<UserDTO> find(String find) {
        if (find == null) return null;
        Set<User> users = new HashSet<>();
        users.addAll(userRepo.findUsersByFirstnameContainsIgnoreCase(find));
        users.addAll(userRepo.findUsersByUsernameContainsIgnoreCase(find));
        users.addAll(userRepo.findUsersByLastnameContainsIgnoreCase(find));
        return convert(users);
    }

    public void add(UserDTO userDTO) {
        if (userDTO == null) return;
        if (!userRepo.existsUserById(userDTO.getId())) {
            userRepo.save(converter.fromDTOToUser(userDTO));
        }
    }
}
