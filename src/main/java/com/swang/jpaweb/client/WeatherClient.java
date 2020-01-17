package com.swang.jpaweb.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class WeatherClient {
	public static String getWeather(final String city, final Date date) {
		return "Unknown";
	}
}