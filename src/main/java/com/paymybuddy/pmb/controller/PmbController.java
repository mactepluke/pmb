package com.paymybuddy.pmb.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
public abstract class PmbController {

    protected static final byte USER_NAME = 20;
    protected static final byte BANK_ACCOUNT_NAME = 10;
    protected static final byte PAYMENT_DESCRIPTION = 50;

    protected PmbController() {
    }

    protected boolean emailIsValid(String email) {

        if (!email.isEmpty() && email.length() <= 40 && email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            return true;
        } else {
            log.error("Invalid email format: must be correct and max 40 characters.");
            return false;
        }
    }

    protected boolean passwordIsValid(String password) {

        if (password.length() >= 6 && password.length() <= 20)  {
            return true;
        } else {
            log.error("Invalid password format: must be between 6-20 characters.");
            return false;
        }
    }

    protected boolean ibanIsValid(String iban) {

        if (!iban.isEmpty() && iban.length() <= 34) {
            return true;
        } else {
            log.error("Invalid IBAN format: must be correct and max 34 characters.");
            return false;
        }
    }

    protected HttpStatus checkFindAllResult(String request, int size) {
        if (size == 0) {
            log.info("No items found for request {}: list is empty.", request);
            return NO_CONTENT;
        } else {
            log.info("{} request successful.", request);
            return OK;
        }
    }

    protected void acknowledgeRequest(String type, String email) {
        email = email.toLowerCase();
        log.info("{} request received with email: {}", type, email);
    }

    protected String formatParam(String string, int maxLength) {

        if (string == null || string.isEmpty()) {
            string = "-";
        }

        if (string.length() > maxLength) {
            string = string.substring(0, maxLength-1);
        }

        return string;
    }

}
