package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.BankAccount;
import org.springframework.transaction.annotation.Transactional;

public interface IBankAccountService {

    @Transactional
    BankAccount create(String email, String name, String iban);
}
