package com.hypnotoid.MySite.services;


import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    public User getById(int userId){
        Optional<User> user = userRepo.findById(userId);
        return user.orElse(null);
    }
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    //@Autowired
    public UserService(final UserRepository userRepo) {
        this.userRepo = userRepo;
    }
}
