package com.gabriel.vaultaPass.domain.credential;

public interface CredentialEncryptor {

    String encrypt(String rawPassword);
    String decrypt(String encryptedPassword);

}
