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

    public List<JoggingEntry> findByUser(final String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) return Collections.emptyList();
        return user.get().getEntries().stream().map(EntryService::toDto)
                .collect(Collectors.toList());
    }

    public JoggingEntry findByUserAndId(final String username, int id) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) return null;
        return user.get().getEntries().stream().filter(dao -> dao.getId() == id)
                .map(EntryService::toDto).findFirst().orElse(null);
    }

    public JoggingEntry save(final String username, final JoggingEntry dto) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent() || dto == null) return null;
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

    private static JoggingEntry toDto(Entry dao) { // or use ModelMapper
        JoggingEntry entry = new JoggingEntry();
        BeanUtils.copyProperties(dao, entry);
        return entry;
    }
}