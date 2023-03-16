package com.paymybuddy.pmb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BANK_ACCOUNT")
@ToString
public class BankAccount {

    protected BankAccount() {
    }
    public BankAccount(PmbUser pmbUser, String name, String iban)    {
        this.setPmbUser(pmbUser);
        this.setName(name);
        this.setIban(iban);
        this.setVerified(false);
    }

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
    private String iban;

    @Column(name = "VERIFIED")
    @Getter
    @Setter
    private boolean verified = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    @ToString.Exclude
    private PmbUser pmbUser;

}
