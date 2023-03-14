package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.utils.Wrap;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRecipientService {

    @Transactional(readOnly = true)
    List<Recipient> getAllEnabledAndExisting(String email);
    @Transactional(readOnly = true)
    List<Recipient> getAllPastAndPresent(String email);

    @Transactional
    Wrap<Recipient, String> create(String userEmail, String recipientEmail);

    @Transactional(readOnly = true)
    Recipient getByEmailAndUser(String recipientEmail, PmbUser pmbUser);

    @Transactional(readOnly = true)
    List<Recipient> getAllByEmail(String recipientEmail);

    @Transactional
    Recipient delete(String userEmail, String recipientEmail);

    @Transactional
    Recipient update(Recipient recipient);
}
