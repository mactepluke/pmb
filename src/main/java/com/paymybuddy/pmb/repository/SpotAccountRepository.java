package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.SpotAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SpotAccountRepository extends JpaRepository<SpotAccount, Integer> {

    SpotAccount findByPmbUser(PmbUser pmbUser);

    ArrayList<SpotAccount> findAllByPmbUser(PmbUser pmbUser);
}
