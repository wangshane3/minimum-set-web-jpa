package com.swang.jpaweb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.swang.jpaweb.client.WeatherClient;
import com.swang.jpaweb.dto.JoggingEntry;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

// when used as DTO, allow listed properties as output only, not as input
@JsonIgnoreProperties(value = {"id", "user", "date", "weather"}, allowGetters = true)
@Data
@Entity
public class Entry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    private int duration; // in minutes
    private int distance; // in feet
    private String location; // in format acceptable to the weather API provider
    private String weather; // weather condition from the weather API provider

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public void copyIfNotNUll(final JoggingEntry dto) {
        if (dto.getDate() != null) setDate(dto.getDate());
        if (dto.getLocation() != null) setLocation(dto.getLocation());
        if (dto.getDuration() > 0) setDuration(dto.getDuration());
        if (dto.getDistance() > 0) setDistance(dto.getDistance());
        if (dto.getDate() != null || dto.getLocation() != null) { //reset weather
            setWeather(WeatherClient.getWeather(dto.getLocation(), dto.getDate()));
        }
    }
}