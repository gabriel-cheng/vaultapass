package com.gabriel.vaultaPass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gabriel.vaultaPass.domain.credential.Credential;
import com.gabriel.vaultaPass.domain.credential.CredentialRepository;
import com.gabriel.vaultaPass.domain.user.UserRepository;
import com.gabriel.vaultaPass.exception.CredentialNotFoundException;
import com.gabriel.vaultaPass.exception.CredentialOwnershipException;
import com.gabriel.vaultaPass.exception.ErrorMessageEnum;
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
            .orElseThrow(() -> new UserNotFoundException(ErrorMessageEnum.USER_NOT_FOUND.getMessage()));

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
            .orElseThrow(() -> new CredentialNotFoundException(ErrorMessageEnum.CREDENTIAL_NOT_FOUND.getMessage()));
            ensureOwnership(credential, userId);
            return credential;
    }

    public String revealPassword(String credentialId, String userId) {
        return findByIdAndUser(credentialId, userId).getPassword();
    }

    public Credential update(
        String credentialId,
        String userId,
        String login,
        String password,
        String email,
        String link,
        String description
    ) {
        Credential credential = findByIdAndUser(credentialId, userId);

        if(login != null) {
            credential.updateLogin(login);
        }
        if(password != null) {
            credential.updatePassword(password);
        }
        if(email != null) {
            credential.updateEmail(email);
        }
        if(link != null) {
            credential.updateLink(link);
        }
        if(description != null) {
            credential.updateDescription(description);
        }

        return credentialRepository.save(credential);
    }

    public void delete(String credentialId, String userId) {
        Credential credential = findByIdAndUser(credentialId, userId);
        credentialRepository.delete(credential);
    }

    private void ensureOwnership(Credential credential, String userId) {
        if(!credential.getUserId().equals(userId)) {
            throw new CredentialOwnershipException(ErrorMessageEnum.CREDENTIAL_DOES_NOT_BELONG_TO_USER.getMessage());
        }
    }

}
