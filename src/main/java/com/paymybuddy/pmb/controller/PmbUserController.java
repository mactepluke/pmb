package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.service.IBankAccountService;
import com.paymybuddy.pmb.service.IPmbUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Log4j2
@RestController
@RequestMapping("/pmbuser")
public class PmbUserController {

    private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private final IPmbUserService pmbUserService;

    @Autowired
    public PmbUserController(IPmbUserService pmbUserService) {
        this.pmbUserService = pmbUserService;
    }

    //http://localhost:8080/pmbuser/create?email=<email>&password=<password>
    @PostMapping("/create")
    public ResponseEntity<PmbUser> create(@RequestParam String email, @RequestParam String password) {

        HttpStatus status;
        PmbUser pmbUser = null;

        if (email.isEmpty() || password.length() < 6 || password.length() > 20 || email.length() > 40 || !email.matches(EMAIL_REGEX_PATTERN)) {
            log.error("Invalid post request: email format must be correct and max 40 characters, and password must be between 6-20 characters.");
            status = BAD_REQUEST;
        } else {
            email = email.toLowerCase();

            log.info("Post request received with email:{}, password:{}", email, password.replaceAll(".", "*"));

            pmbUser = pmbUserService.create(email, password);

            if (pmbUser == null) {
                log.error("User already exists with email:{}", email);
                status = NOT_ACCEPTABLE;
            } else {
                log.info("User created with id: {}", (pmbUser.getUserId() == null ? "<no_id>" : pmbUser.getUserId()));
                status = CREATED;
            }
        }
        return new ResponseEntity<>(pmbUser, status);
    }

    @GetMapping("/login")
    public ResponseEntity<PmbUser> login(@RequestParam String email, @RequestParam String password) {

        HttpStatus status;
        PmbUser pmbUser = null;

        if (email.isEmpty() || password.length() < 6 || password.length() > 20 || email.length() > 40 || !email.matches(EMAIL_REGEX_PATTERN)) {
            log.error("Invalid get request: email format must be correct and max 40 characters.");
            status = BAD_REQUEST;
        } else {
            email = email.toLowerCase();

            log.info("Get request received with email:{}", email);

            pmbUser = pmbUserService.getUser(email);

            if (pmbUser == null) {
                log.error("No user found with email:{}", email);
                status = NO_CONTENT;
            } else {
                if (pmbUser.getPassword().equals(password)) {
                    log.debug("Get request successful.");
                    status = OK;
                } else {
                    log.debug("Invalid password.");
                    status = UNAUTHORIZED;
                    pmbUser = null;
                }
            }
        }

        return new ResponseEntity<>(pmbUser, status);
    }

    /*@GetMapping("/findbyemail")
    public ResponseEntity<PmbUser> find(@RequestParam String email) {

        HttpStatus status;
        PmbUser pmbUser = null;

        if (email.isEmpty() || email.length() > 40 || !email.matches(EMAIL_REGEX_PATTERN)) {
            log.error("Invalid get request: email format must be correct and max 40 characters.");
            status = BAD_REQUEST;
        } else {
            email = email.toLowerCase();

            log.info("Get request received with email:{}", email);

            pmbUser = pmbUserService.getUser(email);

            if (pmbUser == null) {
                log.error("No user found with email:{}", email);
                status = NO_CONTENT;
            } else {
                log.debug("Get request successful.");
                status = OK;
            }
        }

        return new ResponseEntity<>(pmbUser, status);
    }*/
}
