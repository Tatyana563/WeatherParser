package com.parser.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class WeatherController {
@Autowired
WeatherServiceImpl service;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/weather")
    @ResponseBody
    public WeatherDto getForecast(@RequestParam("q") String city, @RequestParam("appid") String appId, @RequestParam("cnt") String days) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String input = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, appId);
        String input2 = String.format("http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&cnt=%s&appid=%s", city, days, appId);
        WeatherDto weatherDto = restTemplate.exchange(input2, HttpMethod.GET, entity, WeatherDto.class).getBody();
      //  service.save(weatherDto);
        return weatherDto;
    }
}
//Working url
//api.openweathermap.org/data/2.5/weather?q=London&appid=c2e489273fdc0df57969e7049a9a37b0
//myUrl
//http://localhost:8080/weather?q=London&appid=c2e489273fdc0df57969e7049a9a37b0
//http://localhost:8080/weather?q=London&appid=c2e489273fdc0df57969e7049a9a37b0&cnt=3
//forecast for 16 days
//api.openweathermap.org/data/2.5/forecast/daily?q={city name}&cnt={cnt}&appid={your api key}