package com.gabriel.vaultaPass.domain.user;

public interface PasswordEncoder {

    String encode(String rawPassword);
    boolean matches(String rawPassword, String encondedPassword);

}
