package com.gabriel.vaultaPass.domain.user;

public class User {

    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String profilePhotoUrl;

    public User(
        String name,
        String lastname,
        String username,
        String email,
        String password,
        String profilePhotoUrl
    ) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name is mandatory.");
        }
        if(lastname == null || lastname.isBlank()) {
            throw new IllegalArgumentException("The lastname is mandatory.");
        }
        if(username == null || username.isBlank()) {
            throw new IllegalArgumentException("The username is mandatory.");
        }
        if(email == null || !email.contains("@")) {
            throw new IllegalArgumentException("The email address provided is invalid; please try another one.");
        }
        if(password == null || password.isBlank()) {
            throw new IllegalArgumentException("The password is mandatory.");
        }
        if(profilePhotoUrl != null && !isValidUrl(profilePhotoUrl)) {
            throw new IllegalArgumentException("Invalid profile picture url");
        }
        
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

}
