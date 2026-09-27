package com.gabriel.vaultaPass.dto.request.credential;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCredentialPasswordRequestDTO(
    @Size(min = 3, message = "The platform name must be at least 3 characters long.")
    @NotBlank(message = "Password is required.")
    String password
) {}
