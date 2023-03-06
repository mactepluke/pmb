package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.SpotAccount;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface ISpotAccountService {
    ArrayList<SpotAccount> findAll(String email);
    
    SpotAccount create(String email, String currency);


}
