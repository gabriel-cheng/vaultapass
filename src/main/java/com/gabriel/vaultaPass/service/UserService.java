package com.gabriel.vaultaPass.service;

import org.springframework.stereotype.Service;

import com.gabriel.vaultaPass.domain.user.User;
import com.gabriel.vaultaPass.domain.user.UserRepository;

@Service 
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(
        String name,
        String lastname,
        String username,
        String email,
        String password
    ) {
        
    }

}
