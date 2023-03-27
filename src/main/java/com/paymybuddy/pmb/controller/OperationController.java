package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.Operation;
import com.paymybuddy.pmb.model.SpotAccount;
import com.paymybuddy.pmb.service.IOperationService;
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
@RequestMapping("/operation")
@Scope("request")
public class OperationController extends PmbController {

    private final IOperationService operationService;

    @Autowired
    public OperationController(IOperationService operationService) {
        super();
        this.operationService = operationService;
    }

    //http://localhost:8080/operation/credit?email=<email>&iban=<iban>&amount=<amount>
    @PostMapping("/credit")
    public ResponseEntity<Operation> credit(@RequestParam String email,
                                              @RequestParam String iban,
                                              @RequestParam double amount,
                                              @RequestBody SpotAccount spotAccount) {

        HttpStatus status;
        Operation operation;

        String request = "Credit " + amount + " " + spotAccount.getCurrency() + " from " + iban;
        email = email.toLowerCase();
        acknowledgeRequest(request, email);

        if (emailIsValid(email) && ibanIsValid(iban)) {

            operation = operationService.credit(email, iban, spotAccount.getCurrency(), amount);

            if (operation != null) {
                status = OK;
            } else {
                log.error("Could not credit spot account.");
                status = INTERNAL_SERVER_ERROR;
            }
        } else {
            log.error("Invalid parameters.");
            status = BAD_REQUEST;
            operation = null;
        }
        return new ResponseEntity<>(operation, status);
    }


    //http://localhost:8080/operation?email=<email>&iban=<iban>&amount=<amount>
    @PostMapping("/withdraw")
    public ResponseEntity<Operation> withdraw(@RequestParam String email,
                                                @RequestParam String iban,
                                                @RequestParam double amount,
                                                @RequestBody SpotAccount spotAccount) {

        HttpStatus status;
        Operation operation;


        String request = "Withdraw " + amount + " " + spotAccount.getCurrency() + " to " + iban;
        email = email.toLowerCase();
        acknowledgeRequest(request, email);

        if (emailIsValid(email) && ibanIsValid(iban)) {

            operation = operationService.withdraw(email, iban, spotAccount.getCurrency(), amount);

            if (operation != null) {
                status = OK;
            } else {
                log.error("Could not withdraw to bank account (funds may be insufficient).");
                status = INTERNAL_SERVER_ERROR;
            }
        } else {
            log.error("Invalid parameters.");
            status = BAD_REQUEST;
            operation = null;
        }
        return new ResponseEntity<>(operation, status);
    }


    //http://localhost:8080/operation/findAll/<email>
    @GetMapping("/findAll/{email}")
    public ResponseEntity<List<Operation>> findAll(@PathVariable String email) {

        HttpStatus status;
        List<Operation> operations = null;
        String request = "Find all payments";

        acknowledgeRequest(request, email);

        if (emailIsValid(email)) {
            operations = operationService.getAllOperations(email);
            status = checkFindAllResult(request, operations.size());
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(operations, status);
    }

}
