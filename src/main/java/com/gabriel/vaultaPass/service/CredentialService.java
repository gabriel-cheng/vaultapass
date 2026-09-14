package com.gabriel.vaultaPass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gabriel.vaultaPass.domain.credential.Credential;
import com.gabriel.vaultaPass.domain.credential.CredentialRepository;
import com.gabriel.vaultaPass.domain.user.UserRepository;
import com.gabriel.vaultaPass.exception.CredentialNotFoundException;
import com.gabriel.vaultaPass.exception.CredentialOwnershipException;
import com.gabriel.vaultaPass.exception.ExceptionMessageEnum;
import com.gabriel.vaultaPass.exception.UserNotFoundException;

@Service
public class CredentialService {

    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;

    public CredentialService(CredentialRepository credentialRepository, UserRepository userRepository) {
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
    }

    public Credential register(
        String userId,
        String platformName,
        String login,
        String password,
        String email,
        String link,
        String description
    ) {
        userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(ExceptionMessageEnum.USER_NOT_FOUND.getMessage()));

        Credential credential = new Credential(
            userId, platformName, login, password, email, link, description
        );
        return credentialRepository.save(credential);
    }

    public List<Credential> listByUser(String userId) {
        return credentialRepository.findAllByUserId(userId);
    }

    public Credential findByIdAndUser(String credentialId, String userId) {
        Credential credential = credentialRepository.findById(credentialId)
            .orElseThrow(() -> new CredentialNotFoundException(ExceptionMessageEnum.CREDENTIAL_NOT_FOUND.getMessage()));
            ensureOwnership(credential, userId);
            return credential;
    }

    public String revealPassword(String credentialId, String userId) {
        return findByIdAndUser(credentialId, userId).getPassword();
    }

    public Credential updatePassword(String credentialId, String userId, String newPassword) {
        Credential credential = findByIdAndUser(credentialId, userId);
        credential.updatePassword(newPassword);
        return credentialRepository.save(credential);
    }

    public Credential updateLogin(String credentialId, String userId, String login) {
        Credential credential = findByIdAndUser(credentialId, userId);
        credential.updatePassword(login);
        return credentialRepository.save(credential);
    }

    public Credential updateLink(String credentialId, String userId, String link) {
        Credential credential = findByIdAndUser(credentialId, userId);
        credential.updatePassword(link);
        return credentialRepository.save(credential);
    }

    public Credential updateDescription(String credentialId, String userId, String description) {
        Credential credential = findByIdAndUser(credentialId, userId);
        credential.updatePassword(description);
        return credentialRepository.save(credential);
    }

    public Credential updateEmail(String credentialId, String userId, String newEmail) {
        Credential credential = findByIdAndUser(credentialId, userId);
        credential.updateEmail(newEmail);
        return credentialRepository.save(credential);
    }

    public void delete(String credentialId, String userId) {
        Credential credential = findByIdAndUser(credentialId, userId);
        credentialRepository.delete(credential);
    }

    private void ensureOwnership(Credential credential, String userId) {
        if(!credential.getUserId().equals(userId)) {
            throw new CredentialOwnershipException(ExceptionMessageEnum.CREDENTIAL_NOT_BELONGS_YOU.getMessage());
        }
    }

}
