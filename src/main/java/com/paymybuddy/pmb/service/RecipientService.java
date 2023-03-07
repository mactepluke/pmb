package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.repository.RecipientRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

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
    @Transactional
    public Recipient create(String email, String recipientEmail) {

        PmbUser pmbUser = pmbUserService.getUser(email);
        PmbUser pmbRecipientUser = pmbUserService.getUser(recipientEmail);
        Recipient recipient = null;

        if ((pmbUser != null) && (pmbRecipientUser != null)) {

            recipient = new Recipient();
            recipient.setPmbUser(pmbUser);
            recipient.setRecipientId(pmbRecipientUser.getUserId());
            recipient.setEnabled(true);
            recipient = recipientRepository.save(recipient);
        }
        return recipient;
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<Recipient> getRecipients(PmbUser pmbUser) {
        return recipientRepository.findAllByPmbUser(pmbUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Recipient> getById(int recipientId) {
        return recipientRepository.findById(recipientId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUser(PmbUser pmbUser) {
        return recipientRepository.existsByPmbUser(pmbUser);
    }

}
