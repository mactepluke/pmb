package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers()    {
        return userRepository.findAll();
    }

    @Override
    public User create(String email, String password) {
        User user = new User();

        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

}
