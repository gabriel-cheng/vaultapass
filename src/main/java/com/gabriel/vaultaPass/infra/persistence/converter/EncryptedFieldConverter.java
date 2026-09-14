package com.gabriel.vaultaPass.infra.persistence.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.vaultaPass.domain.credential.CredentialEncryptor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
@Component
public class EncryptedFieldConverter implements AttributeConverter<String, String> {

    private static CredentialEncryptor encryptor;

    @Autowired
    public void setEncryptor(CredentialEncryptor encryptor) {
        EncryptedFieldConverter.encryptor = encryptor;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if(attribute == null) {
            return null;
        }
        return encryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        return encryptor.decrypt(dbData);
    }

}
