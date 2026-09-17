package com.gabriel.vaultaPass.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequestDTO(
    @NotBlank
    @Size(
        min = 8, message = "The password must be at least 8 characters long."
    ) String password
) {}
