package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.*;
import com.paymybuddy.pmb.repository.PaymentRepository;
import com.paymybuddy.pmb.utils.Wrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public List<Payment> getAllTransfers(String email) {

        List<Payment> payments;

        payments = getAllEmitted(email);
        payments.addAll(getAllReceived(email));

        for (Payment payment : payments) {
            payment.setRecipientEmail(payment.getRecipient().getRecipientPmbUser().getEmail());
            payment.setEmitterEmail(payment.getRecipient().getPmbUser().getEmail());
        }
        return payments;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllEmitted(String email) {
        return getAllToRecipients(recipientService.getAllByUser(pmbUserService.getByEmail(email)));

    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllReceived(String email) {
        return getAllToRecipients(recipientService.getAllByRecipientUser(pmbUserService.getByEmail(email)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllToRecipients(List<Recipient> recipients) {

        List<Payment> payments = new ArrayList<>();

        for (Recipient recipient : recipients) {
            payments.addAll(paymentRepository.findAllByRecipient(recipient));
        }
        return payments;
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Wrap<Payment, Boolean> create(String emitterEmail, String receiverEmail, String description, double netAmount, String currency) {

        Payment payment = null;
        boolean usersFound = false;

        if (netAmount > 0 && !emitterEmail.equals(receiverEmail)) {

            PmbUser emitterUser = pmbUserService.getByEmail(emitterEmail);
            PmbUser recipientUser = pmbUserService.getByEmail(receiverEmail);
            Recipient recipient = null;

            if (recipientUser != null) {
                recipient = recipientService.getByUsers(emitterUser, recipientUser);
            }

            if ((emitterUser != null) && (recipientUser != null) && (recipient != null) && (recipient.isEnabled())) {

                usersFound = true;
                SpotAccount emitterSpotAccount = spotAccountService.getByUserAndCurrency(emitterUser, currency);
                double grossAmount = round((netAmount + (netAmount * FEE_PERCENT / 100.00)) * 100.00) / 100.00;

                if (emitterSpotAccount != null) {

                    if (emitterSpotAccount.getCredit() < grossAmount) {
                        grossAmount = emitterSpotAccount.getCredit();
                        netAmount = round((grossAmount - (grossAmount * FEE_PERCENT / 100.00)) * 100.00) / 100.00;
                    }

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

                }
            }
        }
        return new Wrap.Wrapper<Payment, Boolean>().put(payment).setTag(usersFound).wrap();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean processPayment(SpotAccount emitterSpotAccount, SpotAccount receiverSpotAccount, double grossAmount, double netAmount) {

        emitterSpotAccount.setCredit(emitterSpotAccount.getCredit() - grossAmount);
        receiverSpotAccount.setCredit((receiverSpotAccount.getCredit() + netAmount));

        return ((emitterSpotAccount == spotAccountService.update(emitterSpotAccount))
                && (receiverSpotAccount == spotAccountService.update(receiverSpotAccount)));
    }


}

