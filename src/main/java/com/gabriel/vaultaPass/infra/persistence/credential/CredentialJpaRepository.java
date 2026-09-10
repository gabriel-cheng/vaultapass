package com.gabriel.vaultaPass.infra.persistence.credential;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialJpaRepository extends JpaRepository<CredentialEntity, String> {
    List<CredentialEntity> findAllByUserId(String userId);
}
