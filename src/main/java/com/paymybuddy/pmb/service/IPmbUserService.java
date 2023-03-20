package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IPmbUserService {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    PmbUser create(String email, String password);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    PmbUser update(String email, PmbUser newUser);

    @Transactional(readOnly = true)
    PmbUser getByEmail(String email);

    @Transactional(readOnly = true)
    Optional<PmbUser> getById(Integer recipientId);
}
