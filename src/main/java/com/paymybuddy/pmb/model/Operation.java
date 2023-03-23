package com.paymybuddy.pmb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "OPERATION")
@ToString
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor(access=AccessLevel.PROTECTED)
public class Operation {

    // Empty public or protected constructor is required by Spring JPA to create the entity, and the default one does not exist anymore due to the use of Lombok's @Builder
    protected Operation()    {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPERATION_ID")
    @Getter
    private Integer operationId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BANK_ACCOUNT_ID")
    @Getter
    @Setter
    private BankAccount bankAccount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SPOT_ACCOUNT_ID")
    @Getter
    @Setter
    private SpotAccount spotAccount;

    @Column(name = "DATE_TIME")
    @Getter
    @Setter
    private LocalDateTime dateTime;

    @Column(name = "AMOUNT")
    @Getter
    @Setter
    private double amount;

    @Column(name = "WITHDRAWAL")
    @Getter
    @Setter
    private boolean withdrawal;

    @Column(name = "PROCESSED")
    @Getter
    @Setter
    @Builder.Default
    private boolean processed = false;

    @Transient
    @Getter
    @Setter
    private String spotAccountCurrency;

    @Transient
    @Getter
    @Setter
    private String bankAccountIban;
}
