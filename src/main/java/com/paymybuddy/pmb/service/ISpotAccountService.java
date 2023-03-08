package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.SpotAccount;
import com.paymybuddy.pmb.utils.Wrap;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface ISpotAccountService {
    @Transactional
    ArrayList<SpotAccount> findAll(String email);

    @Transactional
    Wrap<SpotAccount, Boolean> create(String email, String currency);

    @Transactional
    SpotAccount addIfNotExist(PmbUser pmbUser, String currency);

    @Transactional(readOnly = true)
    SpotAccount getByUserAndCurrency(PmbUser pmbUser, String currency);

    @Transactional
    SpotAccount update(SpotAccount spotAccount);
}
