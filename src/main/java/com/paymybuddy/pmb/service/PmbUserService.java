package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.repository.PmbUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class PmbUserService implements IPmbUserService {

    private final PmbUserRepository pmbUserRepository;

    @Autowired
    public PmbUserService(PmbUserRepository pmbUserRepository)  {
        this.pmbUserRepository = pmbUserRepository;
    }

    @Override
    @Transactional
    public PmbUser create(String email, String password) {

        PmbUser pmbUser = getByEmail(email);

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
    @Transactional
    public PmbUser update(String email, PmbUser newUser) {

        PmbUser pmbUser = getByEmail(email);

        if (pmbUser != null)    {
            pmbUser.setEmail(newUser.getEmail());
            pmbUser.setPassword(newUser.getPassword());
            pmbUser.setFirstName(newUser.getFirstName());
            pmbUser.setLastName(newUser.getLastName());
            pmbUser = pmbUserRepository.save(pmbUser);
        }
        return pmbUser;
    }

    @Override
    @Transactional(readOnly = true)
    public PmbUser getByEmail(String email) {
        return pmbUserRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PmbUser> getById(Integer userId) {
        return pmbUserRepository.findById(userId );
    }

}
