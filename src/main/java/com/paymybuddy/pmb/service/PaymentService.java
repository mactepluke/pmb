package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.*;
import com.paymybuddy.pmb.repository.PaymentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.round;

@Log4j2
@Service
public class PaymentService implements IPaymentService {

    private static final int FEE_PERCENT = 3;

    private final PaymentRepository paymentRepository;
    private final IPmbUserService pmbUserService;
    private final IRecipientService recipientService;
    private final ISpotAccountService spotAccountService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                          IPmbUserService pmbUserService,
                          IRecipientService recipientService,
                          ISpotAccountService spotAccountService
    ) {
        this.paymentRepository = paymentRepository;
        this.pmbUserService = pmbUserService;
        this.recipientService = recipientService;
        this.spotAccountService = spotAccountService;
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<Payment> findAll(String email) {

        ArrayList<Recipient> recipients = recipientService.findAll(email);
        ArrayList<Payment> payments = new ArrayList<>();

        for(Recipient recipient: recipients)  {
            payments.addAll(paymentRepository.findAllByRecipient(recipient));
        }

        return payments;
    }

    @Override
    @Transactional
    public Payment create(String emitterEmail, String receiverEmail, String description, double netAmount, String currency) {

        Payment payment = null;

        if (netAmount > 0) {

            PmbUser emitterUser = pmbUserService.getUser(emitterEmail);
            PmbUser recipientUser = pmbUserService.getUser(receiverEmail);
            Recipient recipient = null;

            if (recipientUser != null) {
                recipient = recipientService.getByIdAndUser(recipientUser.getUserId(), emitterUser);
            }

            if ((emitterUser != null) && (recipientUser != null) && (recipient != null) && (recipient.isEnabled())) {

                SpotAccount emitterSpotAccount = spotAccountService.getByUserAndCurrency(emitterUser, currency);

                double grossAmount = round((netAmount + (netAmount * FEE_PERCENT / 100.00)) * 100.00) / 100.00;

                if ((emitterSpotAccount != null) && (emitterSpotAccount.getCredit() >= grossAmount)) {

                    SpotAccount receiverSpotAccount = spotAccountService.addIfNotExist(recipientUser, currency);

                    AtomicBoolean processed = new AtomicBoolean(false);
                    processed.set(processPayment(emitterSpotAccount, receiverSpotAccount, grossAmount, netAmount));

                    Payment.PaymentBuilder builder = Payment.builder();

                    payment = builder
                            .netAmount(netAmount)
                            .grossAmount(grossAmount)
                            .feePercent(FEE_PERCENT)
                            .currency(currency)
                            .dateTime(LocalDateTime.now())
                            .description(description)
                            .recipient(recipient)
                            .processed(processed.get())
                            .build();

                    payment = paymentRepository.save(payment);

                } else {
                    log.error("Unable to create payment: insufficient funds.");
                }
            } else {
                log.error("Unable to create payment: cannot find one or both user(s).");
            }
        } else {
            log.error("Unable to create payment: incorrect amount.");
        }
        return payment;
    }

    @Override
    @Transactional
    public boolean processPayment(SpotAccount emitterSpotAccount, SpotAccount receiverSpotAccount, double grossAmount, double netAmount) {

        emitterSpotAccount.setCredit(emitterSpotAccount.getCredit() - grossAmount);
        receiverSpotAccount.setCredit((receiverSpotAccount.getCredit() + netAmount));

        return ((emitterSpotAccount == spotAccountService.update(emitterSpotAccount))
                && (receiverSpotAccount == spotAccountService.update(receiverSpotAccount)));
    }
}

