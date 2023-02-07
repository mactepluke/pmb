package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

}
