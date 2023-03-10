package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.Payment;
import com.paymybuddy.pmb.service.IPaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@RestController
@RequestMapping("/payment")
public class PaymentController extends PmbController {

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        super();
        this.paymentService = paymentService;
    }


    //http://localhost:8080/payment/create?user=<user>&buddy=<buddy>&description=<description>&net_amount=<net_amount>&currency=<currency>
    @PostMapping("/create")
    public ResponseEntity<Payment> create(
            @RequestParam(name = "user") String emitterEmail,
            @RequestParam(name = "buddy") String receiverEmail,
            @RequestParam String description,
            @RequestParam(name = "net_amount") double netAmount,
            @RequestParam String currency
    ) {

        HttpStatus status;
        Payment payment = null;

        emitterEmail = emitterEmail.toLowerCase();
        receiverEmail = receiverEmail.toLowerCase();
        log.info("Create payment request received with sender: {}, receiver: {}, amount: {} currency: {}", emitterEmail, receiverEmail, netAmount, currency);

        if (emailIsValid(emitterEmail)) {

            payment = paymentService.create(emitterEmail, receiverEmail, formatParam(description, PAYMENT_DESCRIPTION), netAmount, currency);


            if (payment == null) {
                log.error("Cannot create payment: try to change parameters.");
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

        acknowledgeRequest("Find all payments", email);

        if (emailIsValid(email)) {

            payments = paymentService.findAllEmitted(email);
            status = checkFindAllResult(payments.size());
        } else {
            status = BAD_REQUEST;
        }

        return new ResponseEntity<>(payments, status);
    }
}
