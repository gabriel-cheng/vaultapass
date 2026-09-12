package com.gabriel.vaultaPass.service;

import org.springframework.stereotype.Service;

import com.gabriel.vaultaPass.domain.user.PasswordEncoder;
import com.gabriel.vaultaPass.domain.user.User;
import com.gabriel.vaultaPass.domain.user.UserRepository;
import com.gabriel.vaultaPass.exception.AlreadyExistsException;

@Service 
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(
        String name,
        String lastname,
        String username,
        String email,
        String password
    ) {
        if(userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("E-mail already in use, please provide another one.");
        }
        if(userRepository.existsByUsername(username)) {
            throw new AlreadyExistsException("Username already in use, please provide another one.");
        }
    }

}
