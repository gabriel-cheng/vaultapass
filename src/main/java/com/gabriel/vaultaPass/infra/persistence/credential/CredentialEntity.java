package com.gabriel.vaultaPass.infra.persistence.credential;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "credential")
public class CredentialEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "platform_name")
    private String platformName;

    private String login;
    private String password;
    private String email;
    private String link;
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected CredentialEntity() {}

    public CredentialEntity(
        String id,
        String userId,
        String platformName,
        String login,
        String password,
        String email,
        String link,
        String description,
        LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.platformName = platformName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.link = link;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getPlatformName() { return platformName; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getLink() { return link; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }

}
