package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import org.springframework.transaction.annotation.Transactional;

public interface IPmbUserService {
    @Transactional
    PmbUser create(String email, String password);

    @Transactional(readOnly = true)
    PmbUser getUser(String email);

}
