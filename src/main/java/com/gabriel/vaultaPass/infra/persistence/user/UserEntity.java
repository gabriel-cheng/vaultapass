package com.gabriel.vaultaPass.infra.persistence.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class UserEntity {

    @Id
    @Column(name = "id")
    private String id;

    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private LocalDateTime credentialsUpdatedAt;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected UserEntity() {}

    public UserEntity(
        String id,
        String name,
        String lastname,
        String username,
        String email,
        String password,
        String profilePhotoUrl,
        LocalDateTime createdAt,
        LocalDateTime credentialsUpdatedAt
    ) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePhotoUrl = profilePhotoUrl;
        this.createdAt = createdAt;
        this.credentialsUpdatedAt = credentialsUpdatedAt;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLastname() { return lastname; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getProfilePhotoUrl() { return profilePhotoUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getCredentialsUpdatedAt() { return credentialsUpdatedAt; }

}
