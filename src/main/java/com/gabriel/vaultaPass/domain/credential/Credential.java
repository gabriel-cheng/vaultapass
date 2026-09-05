package com.gabriel.vaultaPass.domain.credential;

public class Credential {

    private String platformName;
    private String login;
    private String password;
    private String link;
    private String description;

    public Credential(
        String platformName,
        String login,
        String password,
        String link,
        String description
    ) {
        if(platformName == null || platformName.isBlank()) {
            throw new IllegalArgumentException("The name is mandatory.");
        }
        if(login == null || platformName.isBlank()) {
            throw new IllegalArgumentException("The name is mandatory.");
        }
        if(password == null || platformName.isBlank()) {
            throw new IllegalArgumentException("The password is mandatory.");
        }
        
        this.platformName = platformName;
        this.login = login;
        this.password = password;
        this.link = link;
        this.description = description;
    }

}
