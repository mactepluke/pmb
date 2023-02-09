package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.service.IPmbUserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/pmbuser")
public class PmbUserController {

    @Autowired
    private IPmbUserService pmbUserService;

    @PostMapping
    //http://localhost:8080/pmbuser?email=<email>&password=<password>
    public ResponseEntity<PmbUser> create(@RequestParam String email, @RequestParam String password)   {

        log.info("Post request received with email:{}, password: {}", email, password);
        PmbUser pmbUser = pmbUserService.create(email, password);

        return new ResponseEntity<>(pmbUser, HttpStatus.CREATED);
    }

    @GetMapping
    public void get()   {

        log.debug("Get request successful.");

    }

}
