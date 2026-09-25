package com.gabriel.vaultaPass.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUsernameRequestDTO(
    @NotBlank
    @Size(
        min = 3, message = "The username must be at least 3 characters long."
    ) String username
) {}
