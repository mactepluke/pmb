package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;

import java.util.List;

public interface IPmbUserService {

    List<PmbUser> getUsers();

    PmbUser create(String email, String password);
}
