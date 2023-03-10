package com.paymybuddy.pmb.service;
import com.paymybuddy.pmb.model.Payment;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.model.SpotAccount;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPaymentService {

    @Transactional(readOnly = true)
    List<Payment> getAllEmitted(String email);

    @Transactional(readOnly = true)
    List<Payment> getAllReceived(String email);

    @Transactional(readOnly = true)
    List<Payment>  getAllToRecipients(List<Recipient> recipients);

    @Transactional
    Payment create(String emitterEmail, String receiverEmail, String description, double netAmount, String currency);

    @Transactional
    boolean processPayment(SpotAccount emitterSpotAccount, SpotAccount receiverSpotAccount, double grossAmount, double netAmount);
}
