package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.model.SpotAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Integer> {

    ArrayList<Recipient> findAllByPmbUser(PmbUser pmbUser);

    boolean existsByPmbUser(PmbUser pmbUser);
}