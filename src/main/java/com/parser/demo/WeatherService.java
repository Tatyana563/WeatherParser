package com.parser.demo;

import com.parser.demo.dto.WeatherResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {
    void save(WeatherResponseDto weatherDto);
}
