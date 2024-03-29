package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.PmbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    ArrayList<BankAccount> findAllByPmbUser(PmbUser pmbUser);

    BankAccount findByPmbUserAndIban(PmbUser pmbUser, String iban);

    BankAccount findByIban(String iban);

    List<BankAccount> findAllByPmbUserAndEnabled(PmbUser pmbUser, boolean enabled);
}