package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.repository.BankAccountRepository;
import com.paymybuddy.pmb.utils.Wrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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
    @Transactional(readOnly = true)
    public ArrayList<BankAccount> findAll(String email) {
        return bankAccountRepository.findAllByPmbUser(pmbUserService.getUser(email));
    }

    @Override
    @Transactional
    public Wrap<BankAccount, Boolean> create(String email, String name, String iban) {

        BankAccount bankAccount = null;
        boolean created = false;
        PmbUser pmbUser = pmbUserService.getUser(email);

        if (pmbUser != null) {
            bankAccount = getByUserAndIban(pmbUser, iban);

            if (bankAccount == null) {
                bankAccount = new BankAccount(pmbUser, name, iban);
                bankAccount = bankAccountRepository.save(bankAccount);
                created = true;
            }

        } else {
            log.error("Cannot find user with email: {}", email);
        }
        return new Wrap.Wrapper<BankAccount, Boolean>().put(bankAccount).setTag(created).wrap();
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccount getByUserAndIban(PmbUser pmbUser, String iban) {
        return bankAccountRepository.findByPmbUserAndIban(pmbUser, iban);
    }

}
