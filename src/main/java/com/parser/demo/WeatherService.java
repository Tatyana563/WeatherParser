package com.parser.demo;

import org.springframework.stereotype.Service;

@Service
public interface WeatherService {
    void save(WeatherDto weatherDto);
}
