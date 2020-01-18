package com.swang.jpaweb.service;

import com.swang.jpaweb.client.WeatherClient;
import com.swang.jpaweb.dto.JoggingEntry;
import com.swang.jpaweb.model.Entry;
import com.swang.jpaweb.model.User;
import com.swang.jpaweb.repo.EntryRepository;
import com.swang.jpaweb.repo.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class EntryService {
    private final UserRepository userRepository;
    private final EntryRepository entryRepository;

    public EntryService(UserRepository uRepo, EntryRepository eRepo) {
        userRepository = uRepo;
        entryRepository = eRepo;
    }

    public List<JoggingEntry> findAll() {
        final String username = getUsername();
        if (username == null)  return Collections.emptyList();
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) return Collections.emptyList();
        return user.get().getEntries().stream().map(JoggingEntry::valueOf)
                .collect(Collectors.toList());
    }

    public JoggingEntry findById(int id) {
        final String username = getUsername();
        if (username == null)  return null;
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) return null;
        return user.get().getEntries().stream().filter(dao -> dao.getId() == id)
                .map(JoggingEntry::valueOf).findFirst().orElse(null);
    }

    public JoggingEntry save(final JoggingEntry dto) {
        final String username = getUsername();
        if (username == null || dto == null)  return null;
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) return null; // user must register and login
        if (dto.getDate() == null) { // default date to today
            dto.setDate(Calendar.getInstance().getTime());
        }
        // get from a weather provider
        dto.setWeather(WeatherClient.getWeather(dto.getLocation(), dto.getDate()));
        Entry dao = new Entry();
        BeanUtils.copyProperties(dto, dao);
        dao.setUser(user.get());
        entryRepository.saveAndFlush(dao);
        return dto;
    }

    public void delete(int id) {
        entryRepository.deleteById(id);
    }

    public JoggingEntry update(int id, JoggingEntry dto) {
        final String username = getUsername();
        if (username == null || dto == null)  return null;
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) return null; // user must register and login
        Optional<Entry> entry = entryRepository.findById(id);
        if (!entry.isPresent()) return null; // do nothing if record does not exist in DB
        Entry found = entry.get();
        if (!found.getUser().equals(user.get())) return null; // disallow change other's record
        found.copyIfNotNUll(dto);  // use properties in dto if set/changing
        return dto; // when a DAO, found is changed, JPA updates DB automatically
    }

    // another way to get logged-in user name is let Spring inject Principal
    private String getUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}