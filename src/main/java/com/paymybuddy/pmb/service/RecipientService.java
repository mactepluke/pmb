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
import java.util.List;

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

    @Override
    @Transactional(readOnly = true)
    public List<Recipient> getAllEnabledAndExisting(String email) {

        List<Recipient> recipients = recipientRepository.findAllByPmbUserAndEnabled(pmbUserService.getByEmail(email), true);
        List<Recipient> existingRecipients = new ArrayList<>();

        for(Recipient recipient: recipients)  {
            if (pmbUserService.getById(recipient.getRecipientId()).isPresent()) {
                existingRecipients.add(recipient);
            }
        }
        return existingRecipients;
    }

    @Transactional(readOnly = true)
    public List<Recipient> getAllPastAndPresent(String email) {
        return recipientRepository.findAllByPmbUser(pmbUserService.getByEmail(email));
    }

    @Override
    @Transactional
    public Wrap<Recipient, String> create(String userEmail, String recipientEmail) {

        Recipient recipient = null;
        String response = "NO";
        PmbUser pmbUser = pmbUserService.getByEmail(userEmail);
        PmbUser recipientUser = pmbUserService.getByEmail(recipientEmail);

        if ((pmbUser != null) && (recipientUser != null)) {

            recipient = getByEmailAndUser(recipientEmail, pmbUser);

            if (recipient == null) {
                recipient = new Recipient(pmbUser, recipientEmail);
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
    public Recipient getByEmailAndUser(String recipientEmail, PmbUser pmbUser)    {
        return recipientRepository.findByRecipientEmailAndPmbUser(recipientEmail, pmbUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipient> getAllByEmail(String recipientEmail)    {
        return recipientRepository.findByRecipientEmail(recipientEmail);
    }

}
