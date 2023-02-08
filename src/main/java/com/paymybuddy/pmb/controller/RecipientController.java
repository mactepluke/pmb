package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.service.IRecipientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/recipient")
public class RecipientController {

    @Autowired
    private IRecipientService recipientService;
}
