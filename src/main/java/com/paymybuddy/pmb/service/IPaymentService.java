package com.paymybuddy.pmb.service;
import com.paymybuddy.pmb.model.Payment;
import org.springframework.transaction.annotation.Transactional;
public interface IPaymentService {
    @Transactional
    Payment create(String emitterEmail, String receiverEmail, String description, float grossAmount, String currency);
}
