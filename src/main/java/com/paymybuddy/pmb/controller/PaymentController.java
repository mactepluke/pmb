package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.Payment;
import com.paymybuddy.pmb.service.IPaymentService;
import com.paymybuddy.pmb.utils.Wrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@RestController
@RequestMapping("/payment")
@Scope("request")
public class PaymentController extends PmbController {

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        super();
        this.paymentService = paymentService;
    }


    //http://localhost:8080/payment/create?user=<user>&recipient=<recipient>
    @PostMapping("/create")
    public ResponseEntity<Payment> create(
            @RequestParam(name = "user") String emitterEmail,
            @RequestParam(name = "recipient") String receiverEmail,
            @RequestBody Payment requestedPayment
    ) {

        HttpStatus status;
        Payment payment = null;
        String description = requestedPayment.getDescription();
        double netAmount = requestedPayment.getNetAmount();
        String currency = requestedPayment.getCurrency();

        emitterEmail = emitterEmail.toLowerCase();
        receiverEmail = receiverEmail.toLowerCase();
        log.info("Create payment request received with sender: {}, receiver: {}, amount: {} currency: {}", emitterEmail, receiverEmail, netAmount, currency);

        if (emailIsValid(emitterEmail)) {

            Wrap<Payment, Boolean> response;
            response = paymentService.create(emitterEmail, receiverEmail, formatParam(description, PAYMENT_DESCRIPTION), netAmount, currency);
            payment = response.unWrap();

            if (payment == null) {
                if (Boolean.TRUE.equals(response.getTag())) {
                    log.error("Cannot create payment: insufficient funds.");
                } else {
                    log.error("Cannot create payment: cannot find one or both users.");
                }
                status = NOT_ACCEPTABLE;
            } else {
                if (payment.isProcessed()) {
                    log.info("Payment processed with id: {}.", (payment.getPaymentId() == null ? "<no_id>" : payment.getPaymentId()));
                    status = CREATED;
                } else {
                    log.info("Unable to process payment. Funds are safu.");
                    status = INTERNAL_SERVER_ERROR;
                }
            }
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(payment, status);
    }

    //http://localhost:8080/payment/findAll/<email>
    @GetMapping("/findAll/{email}")
    public ResponseEntity<List<Payment>> findAll(@PathVariable String email) {

        HttpStatus status;
        List<Payment> payments = null;
        String request = "Find all payments";

        acknowledgeRequest(request, email);

        if (emailIsValid(email)) {
            payments = paymentService.getAllTransfers(email);
            status = checkFindAllResult(request, payments.size());
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(payments, status);
    }
}
