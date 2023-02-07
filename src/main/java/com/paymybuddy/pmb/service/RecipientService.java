package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipientService {

    @Autowired
    RecipientRepository recipientRepository;
}
