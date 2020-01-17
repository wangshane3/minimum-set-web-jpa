package com.swang.jpaweb.dto;

import lombok.Data;

import java.util.Date;

@Data
public class JoggingEntry {
    private Date date;
    private int duration; // in minutes
    private int distance; // in feet
    private String location; // in format acceptable to the weather API provider
    private String weather; // weather condition from the weather API provider
}