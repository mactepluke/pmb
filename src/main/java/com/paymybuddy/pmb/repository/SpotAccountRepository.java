package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.SpotAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotAccountRepository extends JpaRepository<SpotAccount, Integer> {
}