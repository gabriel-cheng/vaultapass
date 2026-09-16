package com.gabriel.vaultaPass.infra.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gabriel.vaultaPass.domain.credential.CredentialEncryptor;
import com.gabriel.vaultaPass.exception.ErrorMessageEnum;


@Component 
public class AesCredentialEncryptor implements CredentialEncryptor {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH_BYTES = 12;
    private static final int TAG_LENGTH_BITS = 128;

    private final SecretKeySpec secretKey;

    public AesCredentialEncryptor(@Value("${app.encryption.secret-key}") String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        this.secretKey = new SecretKeySpec(decodedKey, "AES");
    }

    @Override
    public String encrypt(String rawAttribute) {
        try {
            byte[] iv = generateIv();

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BITS, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

            byte[] encrypted = cipher.doFinal(rawAttribute.getBytes(StandardCharsets.UTF_8));

            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch(Exception ex) {
            throw new IllegalStateException(ErrorMessageEnum.ENCRYPTION_FAILED.getMessage(), ex);
        }
    }

    @Override
    public String decrypt(String encryptAttribute) {
        try {
            byte[] combined = Base64.getDecoder().decode(encryptAttribute);

            byte[] iv = new byte[IV_LENGTH_BYTES];
            byte[] encrypted = new byte[combined.length - IV_LENGTH_BYTES];
            System.arraycopy(combined, 0, iv, 0, IV_LENGTH_BYTES);
            System.arraycopy(combined, IV_LENGTH_BYTES, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BITS, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch(Exception ex) {
            throw new IllegalStateException(ErrorMessageEnum.DECRYPTION_FAILED.getMessage(), ex);
        }
    }

    private byte[] generateIv() {
        byte[] iv = new byte[IV_LENGTH_BYTES];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

}
