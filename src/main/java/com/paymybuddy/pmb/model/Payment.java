package com.paymybuddy.pmb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
@ToString
@Builder
@AllArgsConstructor(access=AccessLevel.PROTECTED)
public class Payment {

    // Empty public or protected constructor is required by Spring JPA to create the entity, and the default one does not exist anymore due to the use of Lombok's @Builder
    protected Payment()    {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    @Getter
    private Integer paymentId;

    @Column(name = "DESCRIPTION")
    @Getter
    @Setter
    @Builder.Default
    private String description = "-";

    @Column(name = "GROSS_AMOUNT")
    @Getter
    @Setter
    private double grossAmount;

    @Column(name = "NET_AMOUNT")
    @Getter
    @Setter
    private double netAmount;

    @Column(name = "FEE_PERCENT")
    @Getter
    @Setter
    private double feePercent;

    @Column(name = "CURRENCY")
    @Getter
    @Setter
    private String currency;

    @Column(name = "DATE_TIME")
    @Getter
    @Setter
    private LocalDateTime dateTime;

    @Column(name = "PROCESSED")
    @Getter
    @Setter
    @Builder.Default
    private boolean processed = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECIPIENT_ID")
    @Getter
    @Setter
    private Recipient recipient;

    @Transient
    @Getter
    @Setter
    private String recipientEmail;

    @Transient
    @Getter
    @Setter
    private String emitterEmail;
}
