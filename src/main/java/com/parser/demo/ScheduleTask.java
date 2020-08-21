package com.parser.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class ScheduleTask {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WeatherServiceImpl service;

    private String city="Milan";
    private String appId="c2e489273fdc0df57969e7049a9a37b0";

    @Scheduled(fixedRate = 5000)
    public void getWeather(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String input = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, appId);
        WeatherDto weatherDto = restTemplate.exchange(input, HttpMethod.GET, entity, WeatherDto.class).getBody();
        service.save(weatherDto);
    }
}
