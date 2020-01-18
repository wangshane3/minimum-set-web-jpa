package com.swang.jpaweb.web;

import com.swang.jpaweb.dto.User;
import com.swang.jpaweb.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService service;

    public RegistrationController(UserService userService) {
        this.service = userService;
    }

    @GetMapping
    public Iterable<User> findAll() {
        return service.findAll();
    }

    @PostMapping
    public User createNewUser(@Valid @RequestBody User user) {
        return User.valueOf(service.saveUser(user));
    }

    @PutMapping("/{user}")
    public User updateUser(@PathVariable String user, @Valid @RequestBody User dto) {
        return User.valueOf(service.update(user, dto));
    }

    @DeleteMapping("/{username}")
    public void delete(@PathVariable String username) {
        service.delete(username);
    }
}