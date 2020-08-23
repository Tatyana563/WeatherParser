package com.parser.demo;

import com.parser.demo.dto.WeatherResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class ScheduleTask {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WeatherService service;

    private String city="Milan";

    @Value("${weather.api.app-id}")
    private String appId;

    @Scheduled(fixedRate = 5000)
    public void getWeather(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String input = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, appId);
        WeatherResponseDto weatherDto = restTemplate.exchange(input, HttpMethod.GET, entity, WeatherResponseDto.class).getBody();
        service.save(weatherDto);
    }
}
