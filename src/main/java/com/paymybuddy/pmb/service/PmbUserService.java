package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.repository.PmbUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class PmbUserService implements IPmbUserService {

    private final PmbUserRepository pmbUserRepository;

    @Autowired
    public PmbUserService(PmbUserRepository pmbUserRepository)  {
        this.pmbUserRepository = pmbUserRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PmbUser> getUsers()    {
        return pmbUserRepository.findAll();
    }

    @Override
    @Transactional
    public PmbUser create(String email, String password) {

        PmbUser pmbUser = pmbUserRepository.findByEmail(email);

        if (pmbUser == null)    {
            pmbUser = new PmbUser();
            pmbUser.setEmail(email);
            pmbUser.setPassword(password);
            pmbUser = pmbUserRepository.save(pmbUser);
        } else {
            pmbUser = null;
        }
        return pmbUser;
    }

    @Override
    public PmbUser getUser(String email) {
        return pmbUserRepository.findByEmail(email);
    }

}
