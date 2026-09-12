package com.gabriel.vaultaPass.domain.credential;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gabriel.vaultaPass.exception.ExceptionMessageEnum;

public class Credential {

    private final String id;
    private final String userId;
    private String platformName;
    private String login;
    private String password;
    private String email;
    private String link;
    private String description;
    private final LocalDateTime createdAt;

    public Credential(
        String userId,
        String platformName,
        String login,
        String email,
        String password,
        String link,
        String description,
        LocalDateTime createdAt
    ) {
        validateUserId(userId);
        validatePlatformName(platformName);
        validateLogin(login);
        validateEmail(email);
        validatePassword(password);
        validateLink(link);
        
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.platformName = platformName;
        this.login = login;
        this.password = password;
        this.link = link;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public Credential(
        String id,
        String userId,
        String platformName,
        String login,
        String email,
        String password,
        String link,
        String description,
        LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.platformName = platformName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.link = link;
        this.description = description;
        this.createdAt = createdAt;
    }

    private void validateUserId(String userId) {
        if(userId == null || userId.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessageEnum.USER_ID_IS_REQUIRED.getMessage());
        }
    }

    private void validatePlatformName(String platformName) {
        if(platformName == null || platformName.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessageEnum.PLATFORM_NAME_IS_REQUIRED.getMessage());
        }
    }

    private void validateLogin(String login) {
        if(login == null || login.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessageEnum.LOGIN_IS_REQUIRED.getMessage());
        }
    }

    private void validatePassword(String password) {
        if(password == null || password.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessageEnum.PASSWORD_IS_REQUIRED.getMessage());
        }
    }

    private void validateEmail(String email) {
        if(email != null && !email.contains("@")) {
            throw new IllegalArgumentException(ExceptionMessageEnum.INVALID_EMAIL.getMessage());
        }
    }

    private void validateLink(String link) {
        if(link != null && !isValidUrl(link)) {
            throw new IllegalArgumentException(ExceptionMessageEnum.INVALID_URL.getMessage());
        }
    }

    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public String getId() { return this.id; }
    public String getUserId() { return this.userId; }
    public String getPlatformName() { return this.platformName; }
    public String getLogin() { return this.login; }
    public String getPassword() { return this.password; }
    public String getEmail() { return this.email; }
    public String getLink() { return this.link; }
    public String getDescription() { return this.description; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }

}
