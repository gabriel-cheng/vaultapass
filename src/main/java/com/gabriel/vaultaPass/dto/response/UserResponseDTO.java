package com.gabriel.vaultaPass.dto.response;

import java.time.LocalDateTime;

import com.gabriel.vaultaPass.domain.user.User;

public record UserResponseDTO(
    String id,
    String name,
    String lastname,
    String username,
    String email,
    String profilePhotoUrl,
    LocalDateTime createdAt
) {

    public static UserResponseDTO fromDomain(User user, String resolvedPhotoUrl) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getLastname(),
            user.getUsername(),
            user.getEmail(),
            resolvedPhotoUrl,
            user.getCreatedAt()
        );
    }

}
