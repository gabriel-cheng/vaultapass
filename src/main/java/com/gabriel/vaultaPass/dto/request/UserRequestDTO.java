package com.gabriel.vaultaPass.dto.request;

public record UserRequestDTO(
    String name,
    String lastname,
    String username,
    String email,
    String password
) {}
