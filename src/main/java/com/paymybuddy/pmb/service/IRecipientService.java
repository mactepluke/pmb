package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.utils.Wrap;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRecipientService {

    @Transactional(readOnly = true)
    List<PmbUser> getAllEnabledAndExisting(String email);
    @Transactional(readOnly = true)
    List<Recipient> getAllByUser(PmbUser pmbUser);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    Wrap<Recipient, String> create(String userEmail, String recipientEmail);

    @Transactional(readOnly = true)
    Recipient getByUsers(PmbUser pmbUser, PmbUser recipientUser);

    @Transactional(readOnly = true)
    List<Recipient> getAllByRecipientUser(PmbUser recipientPmbUser);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    Recipient delete(String userEmail, String recipientEmail);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    Recipient update(Recipient recipient);
}
