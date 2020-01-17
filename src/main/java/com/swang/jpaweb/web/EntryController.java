package com.swang.jpaweb.web;

import com.swang.jpaweb.dto.JoggingEntry;
import com.swang.jpaweb.service.EntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/jogging")
public class EntryController {
    private final EntryService service;

    public EntryController(EntryService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<JoggingEntry> findAll() {
        String username = getUsername();
        if (username == null)  return Collections.emptyList();
        return service.findByUser(username);
    }

    @GetMapping("/{id}")
    public JoggingEntry findOne(@PathVariable int id) {
        String username = getUsername();
        if (username == null) return null;
        return service.findByUserAndId(username, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JoggingEntry create(@RequestBody JoggingEntry dto) {
        String username = getUsername();
        if (username == null) return null;
        return service.save(username, dto);
    }

    // another way to get logged-in user name is in LoginController: ask Spring to inject Principal
    private String getUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.info("user login is " + username);
        return username;
    }
}