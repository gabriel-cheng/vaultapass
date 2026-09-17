package com.gabriel.vaultaPass.dto.request;

public record CredentialUpdateRequestDTO(
    String login,
    String password,
    String email,
    String link,
    String description
) {}
