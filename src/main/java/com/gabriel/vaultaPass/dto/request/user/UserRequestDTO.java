package com.gabriel.vaultaPass.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

    @NotBlank(message = "The name is required.")
    @Size(min = 2, message = "The name must be at least 2 characters long.")
    String name,

    @NotBlank(message = "The last name is required.")
    @Size(min = 2, message = "The last name must be at least 2 characters long.")
    String lastname,

    @NotBlank(message = "The username is required.")
    @Size(min = 3, message = "The username must be at least 3 characters long.")
    String username,

    @NotBlank(message = "The e-mail is required.")
    @Email(message = "Invalid e-mail.")
    String email,

    @NotBlank(message = "The password is required.")
    @Size(min = 8, message = "The password must be at least 8 characters long.")
    String password

) {}
