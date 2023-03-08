package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

public interface IRecipientService {
    @Transactional
    Recipient create(String email, String recipientEmail);

    @Transactional(readOnly = true)
    ArrayList<Recipient> getRecipients(PmbUser pmbUser);

    @Transactional(readOnly = true)
    Optional<Recipient> getById(int recipientId);

    @Transactional(readOnly = true)
    Recipient getByIdAndUser(Integer userId, PmbUser pmbUser);
}
