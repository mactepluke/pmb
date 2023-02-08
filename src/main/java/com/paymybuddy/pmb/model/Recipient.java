package com.paymybuddy.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RECIPIENT")
@ToString
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPIENT_ID")
    @Getter
    private Integer recipientId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_ID")
    @Getter
    @Setter
    @ToString.Exclude
    private List<Payment> payments = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    private User user;

}
