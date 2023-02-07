package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.SpotAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotAccountRepository extends JpaRepository<SpotAccount, Integer> {
}