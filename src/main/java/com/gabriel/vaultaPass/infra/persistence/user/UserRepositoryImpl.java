package com.gabriel.vaultaPass.infra.persistence.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gabriel.vaultaPass.domain.user.User;
import com.gabriel.vaultaPass.domain.user.UserRepository;

@Repository 
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity saved = jpaRepository.save(UserEntityMapper.toEntity(user));
        return UserEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(String id) {
        return jpaRepository.findById(id).map(UserEntityMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserEntityMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream().map(UserEntityMapper::toDomain).toList();
    }

    @Override
    public void delete(User user) {
        jpaRepository.deleteById(user.getId());
    }

}
