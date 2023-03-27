package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.Operation;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOperationService {

    @Transactional(isolation = Isolation.SERIALIZABLE)
    Operation credit(String email, String iban, String currency, double amount);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    Operation withdraw(String email, String iban, String currency, double amount);

    @Transactional(readOnly = true)
    List<Operation> getAllOperations(String email);
}
