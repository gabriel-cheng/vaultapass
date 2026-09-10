package com.gabriel.vaultaPass.domain.credential;

import java.util.List;
import java.util.Optional;

public interface CredentialRepository {

    Credential save(Credential credential);
    Optional<Credential> findById(String id);
    List<Credential> findAllByUserId(String userId);
    void delete(Credential credential);

}
