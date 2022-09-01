package com.hypnotoid.MySite.converters;

import com.hypnotoid.MySite.dto.UserDTO;
import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.services.RoleService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDTOConverter {
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    public UserDTO fromUserToDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEnabled(user.isEnabled());
        dto.setRoleEditor(roleService.isEditor(user));
        dto.setCart(user.getCart());
        return dto;
    }

    public User fromDTOToUser(UserDTO userDTO) {
        if (userDTO == null) return null;
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        if (userDTO.getPassword().length()<35) userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        user.setPassword(userDTO.getPassword());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEnabled(userDTO.isEnabled());
        if (userDTO.isRoleEditor()) {
            user.setRoles(roleService.set(false, true, false));
        }
        else {
            user.setRoles(roleService.set(false, false, true));
        }
        user.setCart(userDTO.getCart());
        return user;
    }


    public UserDTOConverter(RoleService roleService, BCryptPasswordEncoder encoder) {
        this.roleService = roleService;
        this.encoder = encoder;
    }
}
