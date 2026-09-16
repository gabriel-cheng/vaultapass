package com.gabriel.vaultaPass.dto.request;

public record RequestUpdateCredential(
    String login,
    String password,
    String email,
    String link,
    String description
) {}