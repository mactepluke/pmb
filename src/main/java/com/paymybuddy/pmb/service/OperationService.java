package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.*;
import com.paymybuddy.pmb.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OperationService implements IOperationService {

    private final IPmbUserService pmbUserService;
    private final ISpotAccountService spotAccountService;
    private final IBankAccountService bankAccountService;
    private final OperationRepository operationRepository;

    @Autowired
    public OperationService(IPmbUserService pmbUserService,
                            ISpotAccountService spotAccountService,
                            IBankAccountService bankAccountService,
                            OperationRepository operationRepository) {
        super();
        this.pmbUserService = pmbUserService;
        this.spotAccountService = spotAccountService;
        this.bankAccountService = bankAccountService;
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Operation credit(String email, String iban, String currency, double amount) {

        SpotAccount spotAccount = null;
        BankAccount bankAccount = null;
        Operation operation = null;

        PmbUser user = pmbUserService.getByEmailAndEnabled(email);

        if (user != null) {
            spotAccount = spotAccountService.getByUserAndCurrency(user, currency);
            bankAccount = bankAccountService.getByIban(iban);
        }

        if (spotAccount != null && bankAccount != null && bankAccountService.requestSwiftTransfer("CREDIT", iban, currency, amount)) {
            spotAccount.setCredit(spotAccount.getCredit() + amount);
            spotAccount = spotAccountService.update(spotAccount);

            Operation.OperationBuilder builder = Operation.builder();

            operation = builder
                    .amount(amount)
                    .bankAccount(bankAccount)
                    .spotAccount(spotAccount)
                    .dateTime(LocalDateTime.now())
                    .withdrawal(false)
                    .processed(true)
                    .build();

            operation = operationRepository.save(operation);
        }

        return operation;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Operation withdraw(String email, String iban, String currency, double amount) {

        SpotAccount spotAccount = null;
        BankAccount bankAccount = null;
        Operation operation = null;

        PmbUser user = pmbUserService.getByEmailAndEnabled(email);

        if (user != null) {
            spotAccount = spotAccountService.getByUserAndCurrency(user, currency);
            bankAccount = bankAccountService.getByIban(iban);
        }

        if (bankAccount != null
                && (spotAccount != null)
                && (spotAccount.getCredit() >= amount)
                && bankAccountService.requestSwiftTransfer("WITHDRAWAL", iban, currency, amount)) {

            double newAmount = spotAccount.getCredit() - amount;
            if (newAmount < 0) {
                newAmount = 0;
            }
            spotAccount.setCredit(newAmount);

            spotAccount = spotAccountService.update(spotAccount);

            Operation.OperationBuilder builder = Operation.builder();

            operation = builder
                    .amount(amount)
                    .bankAccount(bankAccount)
                    .spotAccount(spotAccount)
                    .dateTime(LocalDateTime.now())
                    .withdrawal(true)
                    .processed(true)
                    .build();

            operation = operationRepository.save(operation);
        }
        return operation;
    }
}
