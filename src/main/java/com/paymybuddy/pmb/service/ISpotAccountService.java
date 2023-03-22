package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.SpotAccount;
import com.paymybuddy.pmb.utils.Wrap;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ISpotAccountService {
    @Transactional(readOnly = true)
    List<SpotAccount> getAll(String email);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    Wrap<SpotAccount, Boolean> create(String email, String currency);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    SpotAccount addIfNotExist(PmbUser pmbUser, String currency);

    @Transactional(readOnly = true)
    SpotAccount getByUserAndCurrency(PmbUser pmbUser, String currency);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    SpotAccount delete(String email, String currency);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    SpotAccount update(SpotAccount spotAccount);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    SpotAccount credit(String email, String iban, String currency, double amount);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    SpotAccount withdraw(String email, String iban, String currency, double amount);
}
