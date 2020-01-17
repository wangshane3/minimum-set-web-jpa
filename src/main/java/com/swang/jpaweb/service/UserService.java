package com.swang.jpaweb.service;

import com.swang.jpaweb.model.User;
import com.swang.jpaweb.repo.RoleRepository;
import com.swang.jpaweb.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String USER_ROLE = "ROLE_USER";

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        userRepository = userRepo;
        roleRepository = roleRepo;
        passwordEncoder = encoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        log.info("create user for " + user.toString());
        log.info("create user Password for " + user.getPassword());
        // Encode plaintext password
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(1);
        // Set Role to ROLE_USER
        user.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));
        return userRepository.saveAndFlush(user);
    }

    public Iterable<com.swang.jpaweb.dto.User> findAll() {
        return userRepository.findAll().stream().map(com.swang.jpaweb.dto.User::valueOf).collect(Collectors.toList());
    }
}