package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.service.IBankAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/bankaccount")
public class BankAccountController {

    private final IBankAccountService bankAccountService;
    //@Autowired
    //private IBankAccountService bankAccountService;

    @Autowired
    public BankAccountController(IBankAccountService bankAccountService)  {
        this.bankAccountService = bankAccountService;
    }
}
