package com.gabriel.vaultaPass.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRequestDTO(
    @NotBlank
    @Size(min = 8, message = "The password must be at least 8 characters long.")
    String newPassword,

    @NotBlank
    String currentPassword
) {}
