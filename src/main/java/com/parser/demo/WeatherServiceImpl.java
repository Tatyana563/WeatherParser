package com.parser.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    private WeatherRepository weatherRepository;
    @Override
    public void save(WeatherDto weatherDto) {
      weatherRepository.save(weatherDto);
    }}
