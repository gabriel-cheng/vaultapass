package com.gabriel.vaultaPass.infra.persistence.credential;

import com.gabriel.vaultaPass.domain.credential.Credential;

public class CredentialEntityMapper {

    public static Credential toDomain(CredentialEntity entity) {

        return new Credential(
            entity.getId(),
            entity.getUserId(),
            entity.getPlatformName(),
            entity.getLogin(),
            entity.getPassword(),
            entity.getEmail(),
            entity.getLink(),
            entity.getDescription(),
            entity.getCreatedAt()
        );

    }

    public static CredentialEntity toEntity(Credential credential) {

        return new CredentialEntity(
            credential.getId(),
            credential.getUserId(),
            credential.getPlatformName(),
            credential.getLogin(),
            credential.getPassword(),
            credential.getEmail(),
            credential.getLink(),
            credential.getDescription(),
            credential.getCreatedAt()
        );

    }

}
