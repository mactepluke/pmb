package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.repository.BankAccountRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class BankAccountService implements IBankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

}
