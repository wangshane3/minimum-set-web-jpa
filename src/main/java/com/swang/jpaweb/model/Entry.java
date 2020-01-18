package com.swang.jpaweb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

// when used as DTO, allow listed properties as output only, not as input
@JsonIgnoreProperties(value = {"id", "user", "date", "weather"}, allowGetters = true)
@Data @NoArgsConstructor
@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Entry(int duration, int distance, String location) {
        this.duration = duration;
        this.distance = distance;
        this.location = location;
    }
}