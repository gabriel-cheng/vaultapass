package com.gabriel.vaultaPass.dto.request;

public record CredentialRequestDTO(
    String userId,
    String platformName,
    String login,
    String password,
    String email,
    String link,
    String description
) {}
