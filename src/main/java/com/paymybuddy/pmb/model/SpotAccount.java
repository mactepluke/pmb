package com.paymybuddy.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SPOT_ACCOUNT")
@ToString(of = {"spotAccountId", "currency", "credit"})
public class SpotAccount {

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
    private float credit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    private User user;
}
