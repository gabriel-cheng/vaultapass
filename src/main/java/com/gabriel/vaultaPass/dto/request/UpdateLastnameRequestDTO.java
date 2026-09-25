package com.gabriel.vaultaPass.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateLastnameRequestDTO(
    @NotBlank
    @Size(
        min = 2, message = "The last name must be at least 2 characters long."
    ) String lastname
) {}
