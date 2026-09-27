package com.gabriel.vaultaPass.dto.request.credential;

import jakarta.validation.constraints.Size;

public record UpdateCredentialLoginRequestDTO(
    @Size(min = 3, message = "The login must be at least 3 characters long.")
    String login
) {}
