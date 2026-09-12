package com.gabriel.vaultaPass.exception;

public enum ExceptionMessageEnum {

    USER_ID_IS_REQUIRED("The user id is required."),
    PLATFORM_NAME_IS_REQUIRED("The platform name is required."),
    LOGIN_IS_REQUIRED("The login is required."),
    PASSWORD_IS_REQUIRED("The password is required."),
    NAME_IS_REQUIRED("The name is required."),
    LASTNAME_IS_REQUIRED("The lastname is required."),
    USERNAME_IS_REQUIRED("The username is required."),
    INVALID_URL("The URL provided is invalid, please try another one."),
    INVALID_EMAIL("The email address provided is invalid, please try another one.");

    private final String message;

    ExceptionMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}