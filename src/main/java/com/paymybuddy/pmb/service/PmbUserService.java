package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.repository.PmbUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PmbUserService implements IPmbUserService {

    @Autowired
    private PmbUserRepository pmbUserRepository;

    @Override
    public List<PmbUser> getUsers()    {
        return pmbUserRepository.findAll();
    }

    @Override
    public PmbUser create(String email, String password) {
        PmbUser pmbUser = new PmbUser();

        pmbUser.setEmail(email);
        pmbUser.setPassword(password);

        return pmbUserRepository.save(pmbUser);
    }

}
