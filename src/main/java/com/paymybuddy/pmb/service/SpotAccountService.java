package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.SpotAccount;
import com.paymybuddy.pmb.repository.SpotAccountRepository;
import com.paymybuddy.pmb.utils.Wrap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class SpotAccountService implements ISpotAccountService {

    private final SpotAccountRepository spotAccountRepository;
    private final IPmbUserService pmbUserService;

    @Autowired
    public SpotAccountService(SpotAccountRepository spotAccountRepository, IPmbUserService pmbUserService) {
        this.spotAccountRepository = spotAccountRepository;
        this.pmbUserService = pmbUserService;
    }


    @Override
    @Transactional
    public List<SpotAccount> getAll(String email) {
        return spotAccountRepository.findAllByPmbUser(pmbUserService.getByEmail(email));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Wrap<SpotAccount, Boolean> create(String email, String currency) {

        SpotAccount spotAccount = null;
        boolean created = false;
        PmbUser pmbUser = pmbUserService.getByEmail(email);

        if (pmbUser != null) {
            spotAccount = getByUserAndCurrency(pmbUser, currency);

            if (spotAccount == null) {
                spotAccount = new SpotAccount(pmbUser, currency);

                spotAccount = spotAccountRepository.save(spotAccount);

                created = true;
            }
        }
        return new Wrap.Wrapper<SpotAccount, Boolean>().put(spotAccount).setTag(created).wrap();
    }

    @Override
    @Transactional
    public SpotAccount addIfNotExist(PmbUser pmbUser, String currency) {

        SpotAccount spotAccount = getByUserAndCurrency(pmbUser, currency);

        if (spotAccount == null) {
            spotAccount = new SpotAccount(pmbUser, currency);
            spotAccount = spotAccountRepository.save(spotAccount);
        }
        return spotAccount;
    }

    @Override
    @Transactional(readOnly = true)
    public SpotAccount getByUserAndCurrency(PmbUser pmbUser, String currency) {
        return spotAccountRepository.findByPmbUserAndCurrency(pmbUser, currency);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SpotAccount delete(String email, String currency) {
        SpotAccount spotAccount = getByUserAndCurrency(pmbUserService.getByEmail(email), currency);

        if (spotAccount != null) {
            spotAccountRepository.delete(spotAccount);
        }
        return spotAccount;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SpotAccount update(SpotAccount spotAccount) {
        return spotAccountRepository.save(spotAccount);
    }

}
