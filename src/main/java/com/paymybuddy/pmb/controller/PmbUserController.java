package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.service.IPmbUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Log4j2
@RestController
@RequestMapping("/pmbuser")
public class PmbUserController extends PmbController {

    private final IPmbUserService pmbUserService;

    @Autowired
    public PmbUserController(IPmbUserService pmbUserService) {
        this.pmbUserService = pmbUserService;
    }

    //http://localhost:8080/pmbuser/create
    @PostMapping("/create")
    public ResponseEntity<PmbUser> create(@RequestBody PmbUser requestedUser) {

        HttpStatus status;
        PmbUser pmbUser = null;
        String email = requestedUser.getEmail();
        String password = requestedUser.getPassword();

        acknowledgeRequest("Create user", email);

        if (emailIsValid(email) && passwordIsValid(password))  {

            pmbUser = pmbUserService.create(email, password);

            if (pmbUser == null) {
                log.error("User already exists with email: {}", email);
                status = NOT_ACCEPTABLE;
            } else {
                log.info("User created with id: {}", (pmbUser.getUserId() == null ? "<no_id>" : pmbUser.getUserId()));
                status = CREATED;
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

        acknowledgeRequest("Login", email);

        if (emailIsValid(email) && passwordIsValid(password)) {

            pmbUser = pmbUserService.getByEmail(email);

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

    //http://localhost:8080/pmbuser/update?email=<email>&item=<item>
    @PutMapping("/update")
    public ResponseEntity<PmbUser> update(@RequestParam String email, @RequestParam String item) {
//TODO écrire un premier paramètre de type enum pour le type de paramètre à updater ?
        HttpStatus status;
        PmbUser pmbUser = null;

        status = OK;
        log.debug("Put request successful.");

        return new ResponseEntity<>(pmbUser, status);
    }

}
