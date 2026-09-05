package com.gabriel.vaultaPass.domain.user;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private final String id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String profilePhotoUrl;
    private final LocalDateTime createdAt;

    public User(
        String name,
        String lastname,
        String username,
        String email,
        String password,
        String profilePhotoUrl
    ) {
        validateName(name);
        validateLastname(lastname);
        validateUsername(username);
        validateEmail(email);
        validatePassword(password);
        validateProfilePhotoUrl(profilePhotoUrl);

        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePhotoUrl = profilePhotoUrl;
        this.createdAt = LocalDateTime.now();
    }

    public User(
        String id,
        String name,
        String lastname,
        String username,
        String email,
        String password,
        String profilePhotoUrl,
        LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePhotoUrl = profilePhotoUrl;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name is mandatory.");
        }
    }

    private void validateLastname(String lastname) {
        if(lastname == null || lastname.isBlank()) {
            throw new IllegalArgumentException("The lastname is mandatory.");
        }
    }

    private void validateUsername(String username) {
        if(username == null || username.isBlank()) {
            throw new IllegalArgumentException("The username is mandatory.");
        }
    }

    private void validateEmail(String email) {
        if(email == null || !email.contains("@")) {
            throw new IllegalArgumentException("The email address provided is invalid; please try another one.");
        }
    }

    private void validatePassword(String password) {
        if(password == null || password.isBlank()) {
            throw new IllegalArgumentException("The password is mandatory.");
        }
    }

    private void validateProfilePhotoUrl(String profilePhotoUrl) {
        if(profilePhotoUrl != null && !isValidUrl(profilePhotoUrl)) {
            throw new IllegalArgumentException("Invalid profile photo url");
        }
    }

    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getLastname() { return this.lastname; }
    public String getUsername() { return this.username; }
    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }
    public String getProfilePhotoUrl() { return this.profilePhotoUrl; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }

}
