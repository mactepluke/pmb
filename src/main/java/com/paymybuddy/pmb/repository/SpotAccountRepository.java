package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.SpotAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SpotAccountRepository extends JpaRepository<SpotAccount, Integer> {

    ArrayList<SpotAccount> findAllByPmbUser(PmbUser pmbUser);

    SpotAccount findByPmbUserAndCurrency(PmbUser pmbUser, String currency);

    List<SpotAccount> findAllByPmbUserAndEnabled(PmbUser pmbUser, Boolean enabled);
}
