package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.repository.BankAccountRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class BankAccountService implements IBankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final IPmbUserService pmbUserService;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, IPmbUserService pmbUserService) {
        this.bankAccountRepository = bankAccountRepository;
        this.pmbUserService = pmbUserService;
    }

    @Override
    @Transactional
    public BankAccount create(String email, String name, String iban) {

        BankAccount bankAccount = null;
        PmbUser pmbUser = pmbUserService.getUser(email);

        if (pmbUser != null) {

            bankAccount = new BankAccount();
            bankAccount.setPmbUser(pmbUser);

            bankAccount.setName(name);
            bankAccount.setIban(iban);
            bankAccount = bankAccountRepository.save(bankAccount);
        }
        return bankAccount;
    }

}
