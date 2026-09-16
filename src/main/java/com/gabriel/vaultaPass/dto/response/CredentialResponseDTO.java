package com.gabriel.vaultaPass.dto.response;

import java.time.LocalDateTime;

import com.gabriel.vaultaPass.domain.credential.Credential;

public record CredentialResponseDTO(
    String id,
    String platformName,
    String login,
    String email,
    String link,
    String description,
    LocalDateTime createAt
) {

    public static CredentialResponseDTO fromDomain(Credential credential) {
        return new CredentialResponseDTO(
            credential.getId(),
            credential.getPlatformName(),
            credential.getLogin(),
            credential.getEmail(),
            credential.getLink(),
            credential.getDescription(),
            credential.getCreatedAt()
        );
    }

}
