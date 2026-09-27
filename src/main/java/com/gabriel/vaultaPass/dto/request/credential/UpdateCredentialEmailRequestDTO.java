package com.gabriel.vaultaPass.dto.request.credential;

import jakarta.validation.constraints.Email;

public record UpdateCredentialEmailRequestDTO(
    @Email(message = "The email address provided is invalid. Please try another one.")
    String email
) {}
