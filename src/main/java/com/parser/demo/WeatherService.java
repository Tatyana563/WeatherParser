package com.parser.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {
    WeatherDto save(WeatherDto weatherDto);

    WeatherDto select (String city);
}
