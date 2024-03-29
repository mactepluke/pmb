package com.paymybuddy.pmb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SPOT_ACCOUNT")
@ToString
public class SpotAccount {

    protected SpotAccount() {
        this.enabled = true;
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
        this.enabled = true;
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

    @Column(name = "ENABLED")
    @Getter
    @Setter
    private boolean enabled;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    @ToString.Exclude
    private PmbUser pmbUser;
}
