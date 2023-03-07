package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.Payment;
import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    ArrayList<Payment> findAllByRecipient(Recipient recipient);
}