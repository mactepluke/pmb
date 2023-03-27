package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.utils.Wrap;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBankAccountService {

    @Transactional(readOnly = true)
    List<BankAccount> getAllByUser(String email);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    Wrap<BankAccount, Boolean> create(String email, String name, String iban);

    @Transactional(readOnly = true)
    BankAccount getByIban(String iban);

    @Transactional(readOnly = true)
    BankAccount getByUserAndIban(PmbUser pmbUser, String iban);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    BankAccount delete(String email, String iban);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    BankAccount update(BankAccount bankAccount);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    boolean requestSwiftTransfer(String transaction, String iban, String currency, double amount);

    @Transactional(readOnly = true)
    List<BankAccount> getAllEnabled(String email);
}
