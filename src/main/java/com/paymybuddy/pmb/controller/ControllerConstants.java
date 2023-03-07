package com.paymybuddy.pmb.controller;

public final class ControllerConstants {

    private ControllerConstants() {
    }

    public static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static final String INVALID_USER_CREDENTIALS = "Invalid post request: email format must be correct and max 40 characters, and password must be between 6-20 characters.";

    public static final String INVALID_EMAIL = "Invalid post request: email format must be correct and max 40 characters.";
}
