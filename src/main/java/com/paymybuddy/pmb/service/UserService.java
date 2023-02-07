package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers()    {
        return userRepository.findAll();
    }

}
