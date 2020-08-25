package com.parser.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    private final WeatherRepository weatherRepository;

    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public WeatherDto save(WeatherDto weatherDto) {
        return weatherRepository.save(weatherDto);
    }

    @Override
    public WeatherDto select(String city) {
     return weatherRepository.select(city);
    }


}
