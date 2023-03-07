package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.model.SpotAccount;
import com.paymybuddy.pmb.repository.SpotAccountRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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
    public ArrayList<SpotAccount> findAll(String email) {
        return spotAccountRepository.findAllByPmbUser(pmbUserService.getUser(email));
    }

    @Override
    @Transactional
    public SpotAccount create(String email, String currency) {

        SpotAccount spotAccount = null;
        PmbUser pmbUser = pmbUserService.getUser(email);

        if (pmbUser != null) {

            spotAccount = getByUserAndCurrency(pmbUser, currency);

            if (spotAccount == null) {
                spotAccount = new SpotAccount();
                spotAccount.setPmbUser(pmbUser);

                if ((!currency.equals("USD"))
                        && (!currency.equals("GBP"))
                        && (!currency.equals("CHF"))
                        && (!currency.equals("AUD"))) {
                    currency = "EUR";
                }

                spotAccount.setCurrency(currency);
                spotAccount.setCredit(0);
                spotAccount = spotAccountRepository.save(spotAccount);
            } else {
                log.debug("Spot account already exists.");
            }
        }
        return spotAccount;
    }

    @Override
    @Transactional(readOnly = true)
    public SpotAccount getByUserAndCurrency(PmbUser pmbUser, String currency) {
        return spotAccountRepository.findByPmbUserAndCurrency(pmbUser, currency);
    }


/*    @Override
    @Transactional(readOnly = true)
    public SpotAccount getSpotAccount(PmbUser pmbUser) {
        return spotAccountRepository.findByPmbUser(pmbUser);
    }*/
}
