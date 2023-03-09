package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.repository.RecipientRepository;
import com.paymybuddy.pmb.utils.Wrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Log4j2
@Service
public class RecipientService implements IRecipientService {

    private final RecipientRepository recipientRepository;
    private final IPmbUserService pmbUserService;

    @Autowired
    public RecipientService(RecipientRepository recipientRepository, IPmbUserService pmbUserService) {
        this.recipientRepository = recipientRepository;
        this.pmbUserService = pmbUserService;
    }

    //TODO Must return a list of users, not recipients. Add code into the method.
    @Override
    @Transactional(readOnly = true)
    public ArrayList<Recipient> findAll(String email) {
        return recipientRepository.findAllByPmbUser(pmbUserService.getUser(email));
    }

    @Override
    @Transactional
    public Wrap<Recipient, String> create(String userEmail, String recipientEmail) {

        Recipient recipient = null;
        String response = "NO";
        PmbUser pmbUser = pmbUserService.getUser(userEmail);
        PmbUser recipientUser = pmbUserService.getUser(recipientEmail);

        if ((pmbUser != null) && (recipientUser != null)) {
            int recipientId = recipientUser.getUserId();

            recipient = getByUserAndRecipientId(pmbUser, recipientId);

            if (recipient == null) {
                recipient = new Recipient(pmbUser, recipientId);
                recipient = recipientRepository.save(recipient);
                response = "CREATED";
            } else if (!recipient.isEnabled()) {
                recipient.setEnabled(true);
                response = "ENABLED";
            }

        } else {
            log.error("Cannot find user with email: {} or recipient with email: {}", userEmail, recipientEmail);
        }
        return new Wrap.Wrapper<Recipient, String>().put(recipient).setTag(response).wrap();
    }

    @Override
    @Transactional(readOnly = true)
    public Recipient getByUserAndRecipientId(PmbUser pmbUser, Integer userId) {
        return recipientRepository.findByPmbUserAndRecipientId(pmbUser, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Recipient getByIdAndUser(Integer userId, PmbUser pmbUser)    {
        return recipientRepository.findByRecipientIdAndPmbUser(userId, pmbUser);
    }

}
