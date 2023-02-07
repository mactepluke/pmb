package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
}