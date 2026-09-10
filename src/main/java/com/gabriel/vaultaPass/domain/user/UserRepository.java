package com.gabriel.vaultaPass.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<User> findAll();
    void delete(User user);

}
