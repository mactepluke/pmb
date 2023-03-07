package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.SpotAccount;
import com.paymybuddy.pmb.service.ISpotAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;
import static com.paymybuddy.pmb.controller.ControllerConstants.*;

@Log4j2
@RestController
@RequestMapping("/spotaccount")
public class SpotAccountController {

    private final ISpotAccountService spotAccountService;

    @Autowired
    public SpotAccountController(ISpotAccountService spotAccountService) {
        this.spotAccountService = spotAccountService;
    }

    //http://localhost:8080/spotaccount/create?email=<email>&currency=<currency>&credit=<credit>
    @PostMapping("/create")
    public ResponseEntity<SpotAccount> create(@RequestParam String email, @RequestParam String currency) {

        HttpStatus status;
        SpotAccount spotAccount = null;

        if (email.isEmpty() || email.length() > 40 || !email.matches(EMAIL_REGEX_PATTERN)) {
            log.error(INVALID_EMAIL);
            status = BAD_REQUEST;
        } else {
            email = email.toLowerCase();

            log.info("Create request received with email: {}, currency: {}", email, currency);

            spotAccount = spotAccountService.create(email, currency);

            if (spotAccount == null) {
                log.error("User does not exists with email: {}", email);
                status = NOT_ACCEPTABLE;
            } else {
                log.info("Spot account created with id: {}", (spotAccount.getSpotAccountId() == null ? "<no_id>" : spotAccount.getSpotAccountId()));
                status = CREATED;
            }
        }
        return new ResponseEntity<>(spotAccount, status);
    }

    //http://localhost:8080/spotaccount/findAll/<email>
    @GetMapping("/findAll/{email}")
    public ResponseEntity<ArrayList<SpotAccount>> findAll(@PathVariable String email) {

        HttpStatus status;
        ArrayList<SpotAccount> spotAccounts = null;

        if (email.isEmpty() || email.length() > 40 || !email.matches(EMAIL_REGEX_PATTERN)) {
            log.error(INVALID_EMAIL);
            status = BAD_REQUEST;
        } else {
            email = email.toLowerCase();

            log.info("Get request received with email: {}", email);

            spotAccounts = spotAccountService.findAll(email);

            if (spotAccounts.isEmpty()) {
                log.error("No spot accounts found with email: {}", email);
                status = NO_CONTENT;
            } else {
                log.info("Find All request successful.");
                status = OK;
            }
        }

        return new ResponseEntity<>(spotAccounts, status);
    }

}
