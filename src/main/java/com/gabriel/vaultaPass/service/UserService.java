package com.gabriel.vaultaPass.service;

import org.springframework.stereotype.Service;

import com.gabriel.vaultaPass.domain.user.PasswordEncoder;
import com.gabriel.vaultaPass.domain.user.User;
import com.gabriel.vaultaPass.domain.user.UserRepository;
import com.gabriel.vaultaPass.exception.AlreadyExistsException;
import com.gabriel.vaultaPass.exception.ErrorMessageEnum;
import com.gabriel.vaultaPass.exception.UserNotFoundException;

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
            throw new AlreadyExistsException(ErrorMessageEnum.EMAIL_ALREADY_IN_USE.getMessage());
        }
        if(userRepository.existsByUsername(username)) {
            throw new AlreadyExistsException(ErrorMessageEnum.USERNAME_ALREADY_IN_USE.getMessage());
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(name, lastname, username, email, encodedPassword, null);
        return userRepository.save(user);
    }

    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessageEnum.USER_NOT_FOUND.getMessage()));
    }

    public User updateEmail(String userId, String newEmail) {
        User user = findById(userId);

        if(userRepository.existsByEmail(newEmail)) {
            throw new AlreadyExistsException(ErrorMessageEnum.EMAIL_ALREADY_IN_USE.getMessage());
        }

        user.updateEmail(newEmail);
        return userRepository.save(user);
    }

    public User updatePassword(String userId, String rawNewPassword) {
        User user = findById(userId);
        user.updatePassword(passwordEncoder.encode(rawNewPassword));
        return userRepository.save(user);
    }

    public User updateProfilePhoto(String userId, String newUrl) {
        User user = findById(userId);
        user.updateProfilePhotoUrl(newUrl);
        return userRepository.save(user);
    }

    public void delete(String userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }

}
