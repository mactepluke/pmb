package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.service.IBankAccountService;
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
@RequestMapping("/bankaccount")
@Scope("request")
public class BankAccountController extends PmbController {

    private final IBankAccountService bankAccountService;

    @Autowired
    public BankAccountController(IBankAccountService bankAccountService)  {
        this.bankAccountService = bankAccountService;
    }

    //http://localhost:8080/bankaccount/create?email=<email>&name=<name>&iban=<iban>
    @PostMapping("/create")
    public ResponseEntity<BankAccount> create(@RequestParam String email, @RequestParam String name, @RequestParam String iban) {

        HttpStatus status;
        BankAccount bankAccount = null;

        email = email.toLowerCase();
        name = formatParam(name, BANK_ACCOUNT_NAME);
        iban = formatParam(iban, BANK_ACCOUNT_IBAN);
        log.info("Create bank account request received with email: {}, account name: {}, IBAN: {}", email, name, iban);


        if (emailIsValid(email) && ibanIsValid(iban))  {

            Wrap<BankAccount, Boolean> response;
            response = bankAccountService.create(email, name, iban);
            bankAccount = response.unWrap();

            if (bankAccount == null) {
                log.error("Cannot create bank account: user does not exist with email: {}", email);
                status = NOT_ACCEPTABLE;
            } else {
                if (Boolean.TRUE.equals(response.getTag())) {
                    log.info("Bank account created with id: {}.", (bankAccount.getBankAccountId() == null ? "<no_id>" : bankAccount.getBankAccountId()));
                    status = CREATED;
                } else {
                    log.info("Bank account already exists with iban: {}.", iban);
                    bankAccount = null;
                    status = OK;
                }
            }
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(bankAccount, status);
    }

    //http://localhost:8080/bankaccount/findAll/<email>
    @GetMapping("/findAll/{email}")
    public ResponseEntity<List<BankAccount>> findAll(@PathVariable String email) {

        HttpStatus status;
        List<BankAccount> bankAccounts = null;
        String request = "Find all bank accounts";

        acknowledgeRequest(request, email);

        if (emailIsValid(email)) {
            bankAccounts = bankAccountService.getAll(email);
            status = checkFindAllResult(request, bankAccounts.size());
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(bankAccounts, status);
    }

    //http://localhost:8080/bankaccount/delete?email=<email>&iban=<iban>
    @DeleteMapping("/delete")
    public ResponseEntity<BankAccount> delete(@RequestParam String email, @RequestParam String iban) {

        HttpStatus status;
        BankAccount bankAccount;
        String request = "Delete bank account";

        acknowledgeRequest(request, email);

        bankAccount = bankAccountService.delete(email, iban);

        if (bankAccount != null) {
            log.debug("Bank account with currency {} deleted.", iban);
            status = OK;
        } else {
            log.debug("Cannot delete bank account: resource does not exist.");
            status = NO_CONTENT;
        }

        return new ResponseEntity<>(bankAccount, status);
    }
}
