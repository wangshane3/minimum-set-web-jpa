package com.swang.jpaweb.web;

import com.swang.jpaweb.dto.JoggingAverage;
import com.swang.jpaweb.dto.JoggingEntry;
import com.swang.jpaweb.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jogging")
public class EntryController {
    private final EntryService service;

    public EntryController(EntryService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<JoggingEntry> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public JoggingEntry findOne(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JoggingEntry create(@RequestBody JoggingEntry dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public JoggingEntry update(@PathVariable int id, @RequestBody JoggingEntry dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/report")
    public Iterable<JoggingAverage> report() {
        List<JoggingEntry> entries = service.findAll();
        if (entries == null || entries.isEmpty()) return Collections.emptyList();

        Optional<Date> firstDay = entries.stream().map(JoggingEntry::getDate).min(Date::compareTo);
        if (!firstDay.isPresent()) return Collections.emptyList();

        final Instant dayOne = firstDay.get().toInstant();
        Map<Integer, List<JoggingEntry>> runsByWeek = entries.stream().collect(Collectors
            .groupingBy(e -> (int) (ChronoUnit.DAYS.between(dayOne, e.getDate().toInstant()) / 7)));

        return runsByWeek.entrySet().stream().map(e -> {
            JoggingAverage avg = new JoggingAverage();
            avg.setWeek(e.getKey() + 1); // not 0-based, but first week is week 1
            int totalFeet = e.getValue().stream().mapToInt(JoggingEntry::getDistance).sum();
            int totalMinutes = e.getValue().stream().mapToInt(JoggingEntry::getDuration).sum();
            avg.setSpeed(totalFeet / totalMinutes);
            int daysWorked = (int) e.getValue().stream().count();
            avg.setDailyDistance(totalFeet / daysWorked);
            return avg;
        }).collect(Collectors.toList());
    }
}