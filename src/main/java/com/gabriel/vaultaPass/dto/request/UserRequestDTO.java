package com.gabriel.vaultaPass.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

    @NotBlank(message = "The name is required.")
    String name,

    @NotBlank(message = "The last name is required.")
    String lastname,

    @NotBlank(message = "The username is required.")
    String username,

    @NotBlank(message = "The e-mail is required.")
    @Email(message = "Invalid e-mail.")
    String email,

    @NotBlank(message = "The password is required.")
    @Size(min = 8, message = "The password must be at least 8 characters long.")
    String password

) {}
