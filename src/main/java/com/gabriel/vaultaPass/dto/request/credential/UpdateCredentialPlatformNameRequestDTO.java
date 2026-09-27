package com.gabriel.vaultaPass.dto.request.credential;

import jakarta.validation.constraints.Size;

public record UpdateCredentialPlatformNameRequestDTO(
    @Size(min = 3, message = "The platform name must be at least 3 characters long.")
    String platformName
) {}
