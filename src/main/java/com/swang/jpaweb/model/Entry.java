package com.swang.jpaweb.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date;
    private int duration; // in minutes
    private int distance; // in feet
    private String location; // in format acceptable to the weather API provider
    private String weather; // weather condition from the weather API provider

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}