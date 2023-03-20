package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.service.IRecipientService;
import com.paymybuddy.pmb.utils.Wrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@RestController
@RequestMapping("/recipient")
@Scope("request")
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
                if (response.getTag().equals("SAME"))   {
                    log.error("Recipient and user emails must be different.");
                } else {
                    log.error("Cannot create recipient: cannot find user or recipient.");
                }
                status = NO_CONTENT;
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
    public ResponseEntity<List<PmbUser>> findAll(@PathVariable String email) {

        HttpStatus status;
        List<PmbUser> recipientUsers = null;
        String request = "Find all recipients";

        acknowledgeRequest(request, email);

        if (emailIsValid(email)) {
            recipientUsers = recipientService.getAllEnabledAndExisting(email);
            status = checkFindAllResult(request, recipientUsers.size());
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(recipientUsers, status);
    }

    //http://localhost:8080/recipient/delete?email=<email>&recipient=<recipient>
    @DeleteMapping("/delete")
    public ResponseEntity<Recipient> delete(@RequestParam String email, @RequestParam(name = "recipient") String recipientEmail) {

        HttpStatus status;
        Recipient recipient;
        String request = "Delete recipient";

        acknowledgeRequest(request, recipientEmail);

        recipient = recipientService.delete(email, recipientEmail);

        if (recipient != null) {
            log.debug("Recipient with email {} deleted (i.e disabled).", recipientEmail);
            status = OK;
        } else {
            log.debug("Cannot delete recipient: resource does not exist.");
            status = NO_CONTENT;
        }

        return new ResponseEntity<>(recipient, status);
    }

}
