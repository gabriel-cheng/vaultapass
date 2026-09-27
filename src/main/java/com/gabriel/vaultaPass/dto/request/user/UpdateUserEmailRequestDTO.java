package com.gabriel.vaultaPass.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserEmailRequestDTO(
    @NotBlank
    @Email
    String email,

    @NotBlank
    String currentPassword
) {}
