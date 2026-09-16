package com.gabriel.vaultaPass.exception;

public enum ErrorMessageEnum {

    USER_ID_IS_REQUIRED("The user ID is required."),
    NAME_IS_REQUIRED("The name is required."),
    LAST_NAME_IS_REQUIRED("The last name is required."),
    USERNAME_IS_REQUIRED("The username is required."),
    EMAIL_ALREADY_IN_USE("Email address already in use. Please provide another one."),
    USERNAME_ALREADY_IN_USE("Username already in use. Please provide another one."),
    INVALID_EMAIL("The email address provided is invalid. Please try another one."),
    USER_NOT_FOUND("User not found."),
    PLATFORM_NAME_IS_REQUIRED("The platform name is required."),
    LOGIN_IS_REQUIRED("The login is required."),
    PASSWORD_IS_REQUIRED("The password is required."),
    INVALID_URL("The URL provided is invalid. Please try another one."),
    CREDENTIAL_NOT_FOUND("Credential not found."),
    CREDENTIAL_DOES_NOT_BELONG_TO_USER("The credential does not belong to you."),
    ENCRYPTION_FAILED("An error occurred while encrypting the attribute."),
    DECRYPTION_FAILED("An error occurred while decrypting the attribute.");

    private final String message;

    ErrorMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}