package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.repository.BankAccountRepository;
import com.paymybuddy.pmb.utils.Wrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
    public ArrayList<BankAccount> getAll(String email) {
        return bankAccountRepository.findAllByPmbUser(pmbUserService.getByEmail(email));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Wrap<BankAccount, Boolean> create(String email, String name, String iban) {

        BankAccount bankAccount = null;
        boolean created = false;
        PmbUser pmbUser = pmbUserService.getByEmail(email);

        if (pmbUser != null) {
            bankAccount = getByIban(iban);

            if (bankAccount == null) {
                bankAccount = new BankAccount(pmbUser, name, iban);
                bankAccount = bankAccountRepository.save(bankAccount);
                created = true;
            }
        }
        return new Wrap.Wrapper<BankAccount, Boolean>().put(bankAccount).setTag(created).wrap();
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccount getByIban(String iban) {
        return bankAccountRepository.findByIban(iban);
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccount getByUserAndIban(PmbUser pmbUser, String iban) {
        return bankAccountRepository.findByPmbUserAndIban(pmbUser, iban);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BankAccount delete(String email, String iban) {
        PmbUser user = pmbUserService.getByEmail(email);
        BankAccount bankAccount = getByUserAndIban(user, iban);

        if (bankAccount != null) {
            bankAccountRepository.delete(bankAccount);
        }
        return bankAccount;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean requestSwiftTransfer(String transaction, String iban, String currency, double amount) {

        if (transaction.equals("CREDIT") || transaction.equals("WITHDRAWAL")) {

            log.info("--MOCK-- Requesting SWIFT {} transaction to {} for {} {}", transaction, iban, amount, currency);
            log.info("--MOCK-- SWIFT request approved.");

            return true;
        }
        return false;
    }

}
