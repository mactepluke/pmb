package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.User;

import java.util.List;

public interface IUserService {

    List<User> getUsers();

    User create(String email, String password);
}
