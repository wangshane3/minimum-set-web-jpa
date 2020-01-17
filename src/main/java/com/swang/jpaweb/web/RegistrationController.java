package com.swang.jpaweb.web;

import com.swang.jpaweb.model.User;
import com.swang.jpaweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<com.swang.jpaweb.dto.User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public void createNewUser(@Valid @RequestBody User user) {
        log.info("create user for " + user.toString());
        log.info("create user Password for " + user.getPassword());
        userService.saveUser(user);
    }
}