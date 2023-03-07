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

    @Id
    @Column(name = "RECIPIENT_ID")
    @Getter
    @Setter
    private Integer recipientId;

    @Column(name = "ENABLED")
    @Getter
    @Setter
    private boolean enabled = false;

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
