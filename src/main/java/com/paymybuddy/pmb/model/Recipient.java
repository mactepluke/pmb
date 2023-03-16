package com.paymybuddy.pmb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RECIPIENT")
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipient {

    protected Recipient() {
    }

    public Recipient(PmbUser pmbUser, PmbUser recipientPmbUser) {
        this.setPmbUser(pmbUser);
        this.recipientPmbUser = recipientPmbUser;
        this.enabled = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPIENT_ID")
    @Getter
    @Setter
    private Integer recipientId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RECIPIENT_USER_ID")
    @Getter
    @Setter
    private PmbUser recipientPmbUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Getter
    @Setter
    private PmbUser pmbUser;

    @Column(name = "ENABLED")
    @Getter
    @Setter
    private boolean enabled;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPIENT_ID")
    @Getter
    @Setter
    @ToString.Exclude
    private List<Payment> payments = new ArrayList<>();

}
