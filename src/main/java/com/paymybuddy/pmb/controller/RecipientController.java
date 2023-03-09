package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.service.IRecipientService;
import com.paymybuddy.pmb.utils.Wrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@RestController
@RequestMapping("/recipient")
public class RecipientController extends PmbController {


    private final IRecipientService recipientService;

    @Autowired
    public RecipientController(IRecipientService recipientService) {
        super();
        this.recipientService = recipientService;
    }

    //http://localhost:8080/recipient/create?user=<user>&recipient=<recipient>
    @PostMapping("/create")
    public ResponseEntity<Recipient> create(@RequestParam(name = "user") String userEmail, @RequestParam(name = "recipient") String recipientEmail) {

        HttpStatus status;
        Recipient recipient = null;

        acknowledgeRequest("Create recipient", recipientEmail);

        if (emailIsValid(userEmail))  {

            Wrap<Recipient, String> response;
            response = recipientService.create(userEmail, recipientEmail);
            recipient = response.unWrap();

            if (recipient == null) {
                log.error("Cannot create recipient: user does not exist with email: {}", userEmail);
                status = NOT_ACCEPTABLE;
            } else {
                if (response.getTag().equals("CREATED")) {
                    log.info("Recipient created with id: {}.", (recipient.getRecipientId() == null ? "<no_id>" : recipient.getRecipientId()));
                    status = CREATED;
                } else {
                    if (response.getTag().equals("ENABLED")) {
                        log.info("Recipient enabled for user: {}.", userEmail);
                        status = OK;
                    } else {
                        log.info("Recipient already enabled for user: {}.", userEmail);
                        status = OK;
                    }
                }
            }
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(recipient, status);
    }

    //http://localhost:8080/recipient/findAll/<email>
    @GetMapping("/findAll/{email}")
    public ResponseEntity<ArrayList<Recipient>> findAll(@PathVariable String email) {

        HttpStatus status;
        ArrayList<Recipient> recipients = null;

        acknowledgeRequest("Find all recipients", email);

        if (emailIsValid(email)) {
            recipients = recipientService.findAll(email);
            status = checkFindAllResult(recipients.size());
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(recipients, status);
    }

}
