package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Integer> {

    ArrayList<Recipient> findAllByPmbUserAndEnabled(PmbUser user, boolean enabled);

    ArrayList<Recipient> findAllByPmbUser(PmbUser byEmail);

    Recipient findByRecipientEmailAndPmbUser(String recipientEmail, PmbUser pmbUser);
}