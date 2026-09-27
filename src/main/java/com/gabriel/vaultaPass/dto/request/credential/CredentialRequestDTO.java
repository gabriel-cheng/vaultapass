package com.gabriel.vaultaPass.dto.request.credential;

import jakarta.validation.constraints.NotBlank;

public record CredentialRequestDTO(

    @NotBlank(message = "Platform name is required.")
    String platformName,

    @NotBlank(message = "Login is required.")
    String login,

    @NotBlank(message = "Password is required.")
    String password,

    String email,
    String link,
    String description

) {}
