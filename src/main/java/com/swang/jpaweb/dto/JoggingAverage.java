package com.swang.jpaweb.dto;

import lombok.Data;

@Data
public class JoggingAverage {
    private int week;
    private int speed; // in feet per minute
    private long dailyDistance; // in feet
}