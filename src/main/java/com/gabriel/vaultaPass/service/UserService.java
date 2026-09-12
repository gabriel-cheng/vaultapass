package com.gabriel.vaultaPass.service;

import org.springframework.stereotype.Service;

import com.gabriel.vaultaPass.domain.user.PasswordEncoder;
import com.gabriel.vaultaPass.domain.user.User;
import com.gabriel.vaultaPass.domain.user.UserRepository;
import com.gabriel.vaultaPass.exception.AlreadyExistsException;
import com.gabriel.vaultaPass.exception.ExceptionMessageEnum;

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
        String rawPassword
    ) {
        if(userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException(ExceptionMessageEnum.EMAIL_ALREADY_IN_USE.getMessage());
        }
        if(userRepository.existsByUsername(username)) {
            throw new AlreadyExistsException(ExceptionMessageEnum.USERNAME_ALREADY_IN_USE.getMessage());
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(name, lastname, username, email, encodedPassword, null);
        return userRepository.save(user);
    }

}
