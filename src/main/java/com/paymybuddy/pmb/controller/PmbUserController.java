package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.service.IPmbUserService;
import com.paymybuddy.pmb.service.ISpotAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Log4j2
@RestController
@RequestMapping("/pmbuser")
@Scope("request")
public class PmbUserController extends PmbController {

    private final IPmbUserService pmbUserService;
    private final ISpotAccountService spotAccountService;

    @Autowired
    public PmbUserController(IPmbUserService pmbUserService, ISpotAccountService spotAccountService) {
        this.pmbUserService = pmbUserService;
        this.spotAccountService = spotAccountService;
    }

    //http://localhost:8080/pmbuser/create
    @PostMapping("/create")
    public ResponseEntity<PmbUser> create(@RequestBody PmbUser requestedUser) {

        HttpStatus status;
        PmbUser pmbUser = null;
        String email = requestedUser.getEmail().toLowerCase();
        String password = requestedUser.getPassword();
        requestedUser.setFirstName(formatParam(requestedUser.getFirstName(), USER_NAME));
        requestedUser.setLastName(formatParam(requestedUser.getLastName(), USER_NAME));

        acknowledgeRequest("Create user", email);

        if (emailIsValid(email) && passwordIsValid(password)) {

            pmbUser = pmbUserService.create(email, password);

            if (pmbUser == null) {
                log.error("User already exists with email: {}", email);
                status = OK;
            } else {
                log.info("User created with id: {}", (pmbUser.getUserId() == null ? "<no_id>" : pmbUser.getUserId()));
                status = CREATED;

                if (Boolean.FALSE.equals(spotAccountService.create(email, "DEFAULT").getTag())) {
                    log.error("Could not create default spot account for user of email: {}", email);
                }
            }
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(pmbUser, status);
    }

    //http://localhost:8080/pmbuser/login?email=<email>&password=<password>
    @GetMapping("/login")
    public ResponseEntity<PmbUser> login(@RequestParam String email, @RequestParam String password) {

        HttpStatus status;
        PmbUser pmbUser = null;

        email = email.toLowerCase();
        acknowledgeRequest("Login", email);

        if (emailIsValid(email) && passwordIsValid(password)) {

            pmbUser = pmbUserService.getByEmailAndEnabled(email);

            if (pmbUser == null) {
                log.error("No user found with email: {}", email);
                status = NO_CONTENT;
            } else {
                if (pmbUser.getPassword().equals(password)) {
                    log.info("Login request successful.");
                    status = OK;
                } else {
                    log.error("Invalid password.");
                    status = UNAUTHORIZED;
                    pmbUser = null;
                }
            }
        } else {
            status = BAD_REQUEST;
        }

        return new ResponseEntity<>(pmbUser, status);
    }

    //http://localhost:8080/pmbuser/find/<email>
    @GetMapping("/find/{email}")
    public ResponseEntity<PmbUser> find(@PathVariable String email) {

        HttpStatus status;
        PmbUser pmbUser = null;
        email = email.toLowerCase();

        if (emailIsValid(email)) {
            acknowledgeRequest("Find user", email);

            pmbUser = pmbUserService.getByEmail(email);

            if (pmbUser == null) {
                log.error("No user found with email: {}", email);
                status = NO_CONTENT;
            } else {
                log.info("Find request successful.");
                status = OK;
            }
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(pmbUser, status);
    }

    //http://localhost:8080/pmbuser/update?email=<email>
    @PutMapping("/update")
    public ResponseEntity<PmbUser> update(@RequestParam String email, @RequestBody PmbUser editedUser) {

        HttpStatus status;
        PmbUser pmbUser = null;
        email = email.toLowerCase();

        acknowledgeRequest("Update user", email);

        if (emailIsValid(email) && passwordIsValid(editedUser.getPassword())) {

            editedUser.setFirstName(formatParam(editedUser.getFirstName(), USER_NAME));
            editedUser.setLastName(formatParam(editedUser.getLastName(), USER_NAME));

            pmbUser = pmbUserService.update(email, editedUser);

            if (pmbUser != null) {
                status = OK;
                log.info("Update request successful.");
            } else {
                status = INTERNAL_SERVER_ERROR;
                log.error("Couldn't update user.");
            }
        }
        else    {
            status = BAD_REQUEST;
            }
        return new ResponseEntity<>(pmbUser, status);
    }

}
