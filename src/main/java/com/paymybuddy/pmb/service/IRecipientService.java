package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.utils.Wrap;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRecipientService {

    @Transactional(readOnly = true)
    List<Recipient> findAllEnabledAndExisting(String email);
    @Transactional(readOnly = true)
    List<Recipient> findAllPastAndPresent(String email);

    @Transactional
    Wrap<Recipient, String> create(String userEmail, String recipientEmail);

    @Transactional(readOnly = true)
    Recipient getByEmailAndUser(String recipientEmail, PmbUser pmbUser);
}
