package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.repository.SpotAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotAccountService {

    @Autowired
    private SpotAccountRepository spotAccountRepository;
}
