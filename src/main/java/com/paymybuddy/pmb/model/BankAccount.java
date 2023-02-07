package com.paymybuddy.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BANK_ACCOUNT")
@ToString(of = {"bankAccountId", "name", "iban", "verified"})
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_ACCOUNT_ID")
    @Getter
    private Integer bankAccountId;

    @Column(name = "NAME")
    @Getter
    @Setter
    private String name;

    @Column(name = "IBAN")
    @Getter
    @Setter
    private float iban;

    @Column(name = "VERIFIED")
    @Getter
    @Setter
    private float verified;

}
