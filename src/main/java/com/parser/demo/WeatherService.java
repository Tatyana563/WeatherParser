package com.parser.demo;

import com.parser.demo.dto.AvgTempResponse;
import com.parser.demo.dto.WeatherResponseDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public interface WeatherService {
    void save(WeatherResponseDto weatherDto);

    Optional<AvgTempResponse> avgTempInCityBetweenTwoDates(String cityName, Instant startDate, Instant finalDate);

   // AvgTempResponse avgTempInCityBetweenTwoDatesFilter(String cityName, String startDate, String finalDate);
}
