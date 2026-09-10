package com.gabriel.vaultaPass.infra.persistence.user;

import com.gabriel.vaultaPass.domain.user.User;

public class UserEntityMapper {

    public static User toDomain(UserEntity entity) {

        return new User(
            entity.getId(),
            entity.getName(),
            entity.getLastname(),
            entity.getUsername(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getProfilePhotoUrl(),
            entity.getCreatedAt()
        );

    }

    public static UserEntity toEntity(User user) {

        return new UserEntity(
            user.getId(),
            user.getName(),
            user.getLastname(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getProfilePhotoUrl(),
            user.getCreatedAt()
        );

    }

}
