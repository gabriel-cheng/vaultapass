package com.gabriel.vaultaPass.service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gabriel.vaultaPass.domain.user.FileStorage;
import com.gabriel.vaultaPass.domain.user.PasswordEncoder;
import com.gabriel.vaultaPass.domain.user.User;
import com.gabriel.vaultaPass.domain.user.UserRepository;
import com.gabriel.vaultaPass.dto.response.UserResponseDTO;
import com.gabriel.vaultaPass.exception.AlreadyExistsException;
import com.gabriel.vaultaPass.exception.ErrorMessageEnum;
import com.gabriel.vaultaPass.exception.UserNotFoundException;

@Service 
public class UserService {

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/jpg", "image/png", "image/webp");
    private static final long MAX_SIZE_BYTES = 5 * 1024 * 1024;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorage fileStorage;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileStorage fileStorage) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorage = fileStorage;
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

    public User updateProfilePhoto(String userId, MultipartFile file) {
        validateFile(file);

        User user = findById(userId);

        if(user.getProfilePhotoUrl() != null) {
            fileStorage.delete(user.getProfilePhotoUrl());
        }

        try {
            String objectKey = fileStorage.upload(file.getOriginalFilename(), file.getInputStream(), file.getContentType());
            user.updateProfilePhotoUrl(objectKey);
            return userRepository.save(user);
        } catch(IOException ex) {
            throw new IllegalStateException(ErrorMessageEnum.FILE_PROCESSING_ERROR.getMessage(), ex);
        }
    }

    public void delete(String userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }

    private void validateFile(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessageEnum.FILE_IS_REQUIRED.getMessage());
        }
        if(!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException(ErrorMessageEnum.INVALID_FILE_TYPE.getMessage());
        }
        if(file.getSize() > MAX_SIZE_BYTES) {
            throw new IllegalArgumentException(ErrorMessageEnum.FILE_SIZE_EXCEEDED.getMessage());
        }
    }

    public String resolveProfilePhotoUrl(User user) {
        if(user.getProfilePhotoUrl() == null) {
            return null;
        }

        return fileStorage.generatePresignedUrl(user.getProfilePhotoUrl(), Duration.ofMinutes(15));
    }

    public UserResponseDTO toResponse(User user) {
        String photoUrl = resolveProfilePhotoUrl(user);

        return UserResponseDTO.fromDomain(user, photoUrl);
    }

}
