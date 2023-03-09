package com.paymybuddy.pmb.service;
import com.paymybuddy.pmb.model.Payment;
import com.paymybuddy.pmb.model.SpotAccount;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface IPaymentService {

    @Transactional(readOnly = true)
    ArrayList<Payment> findAll(String email);

    @Transactional
    Payment create(String emitterEmail, String receiverEmail, String description, double netAmount, String currency);

    @Transactional
    boolean processPayment(SpotAccount emitterSpotAccount, SpotAccount receiverSpotAccount, double grossAmount, double netAmount);
}
