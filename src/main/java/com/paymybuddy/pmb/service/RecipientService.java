package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.repository.RecipientRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RecipientService implements IRecipientService {

    @Autowired
    private RecipientRepository recipientRepository;
}
