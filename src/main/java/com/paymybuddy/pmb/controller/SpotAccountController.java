package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.SpotAccount;
import com.paymybuddy.pmb.service.ISpotAccountService;
import com.paymybuddy.pmb.utils.Wrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;


@Log4j2
@RestController
@RequestMapping("/spotaccount")
@Scope("request")
public class SpotAccountController extends PmbController {

    private final ISpotAccountService spotAccountService;

    @Autowired
    public SpotAccountController(ISpotAccountService spotAccountService) {
        super();
        this.spotAccountService = spotAccountService;
    }

    //http://localhost:8080/spotaccount/create?email=<email>&currency=<currency>
    @PostMapping("/create")
    public ResponseEntity<SpotAccount> create(@RequestParam String email, @RequestParam String currency) {

        HttpStatus status;
        SpotAccount spotAccount = null;

        email = email.toLowerCase();
        log.info("Create spot account request received with email: {}, currency: {}", email, currency);

        if (emailIsValid(email)) {

            Wrap<SpotAccount, Boolean> response;
            response = spotAccountService.create(email, currency);
            spotAccount = response.unWrap();

            if (spotAccount == null) {
                log.error("Cannot create spot account: user does not exist with email: {}", email);
                status = NO_CONTENT;
            } else {
                if (Boolean.TRUE.equals(response.getTag())) {
                    log.info("Spot account created with id: {}.", (spotAccount.getSpotAccountId() == null ? "<no_id>" : spotAccount.getSpotAccountId()));
                    status = CREATED;
                } else {
                    log.info("Spot account already exists with currency: {}.", currency);
                    spotAccount = null;
                    status = OK;
                }
            }
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(spotAccount, status);
    }

    //http://localhost:8080/spotaccount/findAll/<email>
    @GetMapping("/findAll/{email}")
    public ResponseEntity<List<SpotAccount>> findAll(@PathVariable String email) {

        HttpStatus status;
        List<SpotAccount> spotAccounts = null;
        String request = "Find all spot accounts";

        acknowledgeRequest(request, email);

        if (emailIsValid(email)) {
            spotAccounts = spotAccountService.getAll(email);
            status = checkFindAllResult(request, spotAccounts.size());
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(spotAccounts, status);
    }

    //http://localhost:8080/spotaccount/delete?email=<email>&currency=<currency>
    @DeleteMapping("/delete")
    public ResponseEntity<SpotAccount> delete(@RequestParam String email, @RequestParam String currency) {

        HttpStatus status;
        SpotAccount spotAccount;
        String request = "Delete spot account";

        acknowledgeRequest(request, email);

        spotAccount = spotAccountService.delete(email, currency);

        if (spotAccount != null) {
            log.debug("Spot account with currency {} deleted.", currency);
            status = OK;
        } else {
            log.debug("Cannot delete spot account: resource does not exist.");
            status = NO_CONTENT;
        }

        return new ResponseEntity<>(spotAccount, status);
    }

}
