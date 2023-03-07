package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.Payment;
import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.Recipient;
import com.paymybuddy.pmb.model.SpotAccount;
import com.paymybuddy.pmb.repository.PaymentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
                          ISpotAccountService spotAccountService) {
        this.paymentRepository = paymentRepository;
        this.pmbUserService = pmbUserService;
        this.recipientService = recipientService;
        this.spotAccountService = spotAccountService;
    }

    @Override
    @Transactional
    public Payment create(String emitterEmail, String receiverEmail, String description, float netAmount, String currency) {

        Payment payment = null;

        if (netAmount > 0) {

            PmbUser emitterUser = pmbUserService.getUser(emitterEmail);
            PmbUser receiverUser = pmbUserService.getUser(receiverEmail);

            if ((emitterUser != null) && (receiverUser != null)) {

                Optional<Recipient> recipient = recipientService.getById(receiverUser.getUserId());

                if (recipient.isPresent() && recipient.get().isEnabled()) {

                    SpotAccount emitterSpotAccount = null;

                    emitterSpotAccount = spotAccountService.getByUserAndCurrency(emitterUser, currency);

                    float grossAmount = netAmount + (netAmount * FEE_PERCENT / 100);

                    if ((emitterSpotAccount != null) && (emitterSpotAccount.getCredit() >= grossAmount)) {

                        SpotAccount receiverSpotAccount = spotAccountService.create(receiverEmail, currency);

                        if (receiverSpotAccount != null) {

//TODO complete core algo here: change spot account credits accordingly

                            payment = new Payment();
                            payment.setNetAmount(netAmount);
                            payment.setGrossAmount(grossAmount);
                            payment.setFeePercent(FEE_PERCENT);
                            payment.setCurrency(currency);
                            payment.setDateTime(LocalDateTime.now());
                            payment.setDescription(description.substring(0, 49));
                            payment.setRecipient(recipient.get());
                            payment.setProcessed(true);

                            payment = paymentRepository.save(payment);

                        } else {
                            log.error("Unable to create payment: cannot locate/create receiver account.");
                        }
                    } else {
                        log.error("Unable to create payment: insufficient funds.");
                    }
                } else {
                    log.error("Unable to create payment: cannot find receiver in user's recipients list.");
                }
            } else {
                log.error("Unable to create payment: cannot find one or both user(s).");
            }
        } else {
            log.error("Unable to create payment: incorrect amount.");
        }
        return payment;
    }


}

