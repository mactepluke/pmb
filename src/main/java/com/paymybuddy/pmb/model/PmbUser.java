package com.paymybuddy.pmb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
@ToString
public class PmbUser {

    private static final String DEFAULT_VALUE = "unknown";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    @Getter
    private Integer userId;

    @Column(name = "EMAIL")
    @Getter
    @Setter
    private String email = DEFAULT_VALUE;

    @Column(name = "PASSWORD")
    @Getter
    @Setter
    private String password = DEFAULT_VALUE;

    @Column(name = "FIRST_NAME")
    @Getter
    @Setter
    private String firstName = DEFAULT_VALUE;

    @Column(name = "LAST_NAME")
    @Getter
    @Setter
    private String lastName = DEFAULT_VALUE;

    @Column(name = "VERIFIED")
    @Getter
    @Setter
    private boolean verified = false;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    @ToString.Exclude
    private List<Recipient> recipients = new ArrayList<>();

    @JsonIgnoreProperties("pmbUser")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    @ToString.Exclude
    private List<SpotAccount> spotAccounts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    @ToString.Exclude
    private List<BankAccount> bankAccounts = new ArrayList<>();

}
