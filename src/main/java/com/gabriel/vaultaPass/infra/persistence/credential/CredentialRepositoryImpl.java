package com.gabriel.vaultaPass.infra.persistence.credential;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gabriel.vaultaPass.domain.credential.Credential;
import com.gabriel.vaultaPass.domain.credential.CredentialRepository;

@Repository 
public class CredentialRepositoryImpl implements CredentialRepository {

    private final CredentialJpaRepository jpaRepository;

    public CredentialRepositoryImpl(CredentialJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Credential save(Credential credential) {
        CredentialEntity saved = jpaRepository.save(CredentialEntityMapper.toEntity(credential));
        return CredentialEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Credential> findById(String id) {
        return jpaRepository.findById(id).map(CredentialEntityMapper::toDomain);
    }

    @Override
    public List<Credential> findAllByUserId(String userId) {
        return jpaRepository.findAllByUserId(userId).stream().map(CredentialEntityMapper::toDomain).toList();
    }

    @Override
    public void delete(Credential credential) {
        jpaRepository.deleteById(credential.getId());
    }

}
