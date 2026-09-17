package com.gabriel.vaultaPass.dto.request;

public record UpdateCredentialRequestDTO(
    String login,
    String password,
    String email,
    String link,
    String description
) {}