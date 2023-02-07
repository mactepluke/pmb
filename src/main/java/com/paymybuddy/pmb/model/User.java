package com.paymybuddy.pmb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USER")
@ToString(of = {"userId", "email", "firstName", "lastName", "verified"})
public class User {

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

}
