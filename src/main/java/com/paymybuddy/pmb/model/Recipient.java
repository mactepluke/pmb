package com.paymybuddy.pmb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    protected Recipient() {
    }

    public Recipient(PmbUser pmbUser, String recipientEmail) {
        this.setPmbUser(pmbUser);
        this.recipientEmail = recipientEmail;
        this.enabled = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPIENT_ID")
    @Getter
    @Setter
    private Integer recipientId;

    @Column(name = "RECIPIENT_EMAIL")
    @Getter
    @Setter
    private String recipientEmail;

    @Column(name = "ENABLED")
    @Getter
    @Setter
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPIENT_ID")
    @Getter
    @Setter
    @ToString.Exclude
    private List<Payment> payments = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    private PmbUser pmbUser;

}
