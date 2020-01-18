package com.swang.jpaweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swang.jpaweb.model.Entry;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class JoggingEntry {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    private int duration; // in minutes
    private int distance; // in feet
    private String location; // in format acceptable to the weather API provider
    private String weather; // weather condition from the weather API provider

    public JoggingEntry(int duration, int distance, String location) {
        this.duration = duration;
        this.distance = distance;
        this.location = location;
    }

    public static JoggingEntry valueOf(final Entry dao) {
        JoggingEntry entry = new JoggingEntry();
        BeanUtils.copyProperties(dao, entry);
        return entry;
    }
}