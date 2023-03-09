package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.utils.Wrap;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface IRecipientService {

    @Transactional(readOnly = true)
    ArrayList<Recipient> findAll(String email);

    @Transactional
    Wrap<Recipient, String> create(String userEmail, String recipientEmail);

    @Transactional(readOnly = true)
    Recipient getByUserAndRecipientId(PmbUser pmbUser, Integer userId);

    @Transactional(readOnly = true)
    Recipient getByIdAndUser(Integer userId, PmbUser pmbUser);
}
