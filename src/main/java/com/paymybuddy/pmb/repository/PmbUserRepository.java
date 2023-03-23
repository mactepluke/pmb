package com.paymybuddy.pmb.repository;

import com.paymybuddy.pmb.model.PmbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PmbUserRepository extends JpaRepository<PmbUser, Integer> {

    PmbUser findByEmail(String email);

    PmbUser findByEmailAndEnabled(String email, boolean b);
}
