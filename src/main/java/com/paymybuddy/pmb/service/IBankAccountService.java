package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.utils.Wrap;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface IBankAccountService {

    @Transactional(readOnly = true)
    ArrayList<BankAccount> findAll(String email);

    @Transactional
    Wrap<BankAccount, Boolean> create(String email, String name, String iban);

    @Transactional(readOnly = true)
    BankAccount getByUserAndIban(PmbUser pmbUser, String iban);
}
