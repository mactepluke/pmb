package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Integer> {

    List<Operation> findAllByBankAccount(BankAccount bankAccount);
}