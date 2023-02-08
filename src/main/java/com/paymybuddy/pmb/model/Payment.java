package com.paymybuddy.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    @Getter
    private Integer paymentId;

    @Column(name = "DESCRIPTION")
    @Getter
    @Setter
    private String description;

    @Column(name = "GROSS_AMOUNT")
    @Getter
    @Setter
    private float grossAmount;

    @Column(name = "NET_AMOUNT")
    @Getter
    @Setter
    private float netAmount;

    @Column(name = "FEE_PERCENT")
    @Getter
    @Setter
    private int feePercent;

    @Column(name = "DATE_TIME")
    @Getter
    @Setter
    private LocalDateTime dateTime;

    @Column(name = "PROCESSED")
    @Getter
    @Setter
    private boolean processed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RECIPIENT_ID")
    @Getter
    @Setter
    private Recipient recipient;

}
