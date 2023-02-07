package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Integer> {
}