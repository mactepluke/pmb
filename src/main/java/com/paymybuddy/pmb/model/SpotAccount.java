package com.paymybuddy.pmb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SPOT_ACCOUNT")
@ToString
public class SpotAccount {

    protected SpotAccount() {
    }
    public SpotAccount(PmbUser pmbUser, String currency)    {
        this.setPmbUser(pmbUser);

        if ((!currency.equals("USD"))
                && (!currency.equals("GBP"))
                && (!currency.equals("CHF"))
                && (!currency.equals("AUD"))) {
            currency = "EUR";
        }

        this.setCurrency(currency);
        this.setCredit(0);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPOT_ACCOUNT_ID")
    @Getter
    private Integer spotAccountId;

    @Column(name = "CURRENCY")
    @Getter
    @Setter
    private String currency;

    @Column(name = "CREDIT")
    @Getter
    @Setter
    private double credit;

    @JsonIgnoreProperties("spotAccounts")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    private PmbUser pmbUser;
}
