package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.model.User;
import com.paymybuddy.pmb.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping(path = "/create")
    @ResponseBody
    public User create(@RequestParam String email, @RequestParam String password)   {

        User result;

        result = userService.create(email, password);

        return result;
    }

}
