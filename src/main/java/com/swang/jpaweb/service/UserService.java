package com.swang.jpaweb.service;

import com.swang.jpaweb.model.User;
import com.swang.jpaweb.repo.RoleRepository;
import com.swang.jpaweb.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
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

    public User saveUser(final com.swang.jpaweb.dto.User user) {
        User dao = new User();
        BeanUtils.copyProperties(user, dao, null, "password", "active", "roles");
        log.debug("what is as input " + user);
        // Encode plaintext password
        dao.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.setActive(1);
        // Set Role to ROLE_USER
        dao.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));
        log.debug("what goes to DB " + user);
        return userRepository.saveAndFlush(dao);
    }

    public Iterable<com.swang.jpaweb.dto.User> findAll() {
        return userRepository.findAll().stream().map(com.swang.jpaweb.dto.User::valueOf).collect(Collectors.toList());
    }

    public void delete(final String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) userRepository.delete(user.get());
    }

    public User update(String usernameOrEmail, com.swang.jpaweb.dto.User dto) {
        User found;
        Optional<User> user = userRepository.findByUsername(usernameOrEmail);
        if (user.isPresent()) { // try with Username
            found = user.get();
            dto.setUsername(usernameOrEmail);
        } else { // second try with email
            Optional<User> user1 = userRepository.findByEmail(usernameOrEmail);
            if (user1.isPresent()) {
                found = user1.get();
                dto.setEmail(usernameOrEmail);
            } else {
                return null; // do nothing if the user does not exist in system
            }
        }
        log.info("what is in DB " + found);
        if (dto.getPassword() != null) { // Encode plaintext password if changing
            found.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getFirstName() != null) { // update only if changing
            found.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) { // update only if changing
            found.setLastName(dto.getLastName());
        }
        if (dto.getUsername() != null) { // update only if changing
            found.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null) { // update only if changing
            found.setEmail(dto.getEmail());
        }
        log.info("what goes to DB " + found);
        // userRepository.save(found); UPDATE is automatic
        return found;
    }
}