package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;

import java.util.List;

public interface IPmbUserService {

    PmbUser create(String email, String password);

    PmbUser getUser(String email);
}
