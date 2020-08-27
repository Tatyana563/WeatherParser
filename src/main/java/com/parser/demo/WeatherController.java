package com.parser.demo;

import com.parser.demo.dto.AvgTempResponse;
import com.parser.demo.dto.WeatherResponseDto;
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
    public void getForecast(@RequestParam("q") String city, @RequestParam("appid") String appId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String input = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, appId);
        WeatherResponseDto weatherDto = restTemplate.exchange(input, HttpMethod.GET, entity, WeatherResponseDto.class).getBody();
       service.save(weatherDto);

    }
//http://localhost:8080//weather/findTopTemp?q=London&start=2020-08-26&finish=2020-08-28
    @GetMapping("/weather/findTopTemp")
    @ResponseBody
    //TODO: заменить String на Instant
    public AvgTempResponse getForecast(@RequestParam("q") String city, @RequestParam("start") String startDate, @RequestParam("finish") String finishDate) {
        AvgTempResponse avgTempResponse = service.avgTempInCityBetweenTwoDates(city, startDate, finishDate);
        System.out.println(avgTempResponse);
        return service.avgTempInCityBetweenTwoDates(city, startDate, finishDate);


    }
//http://localhost:8080/weather?town=Milan
//    @PutMapping
//    public void updatePopulation(@PathVariable("town") String city) {
//       service.select(city);
//    }
}
//Working url
//api.openweathermap.org/data/2.5/weather?q=London&appid=c2e489273fdc0df57969e7049a9a37b0
//myUrl
//http://localhost:8080/weather?q=Milan&appid=c2e489273fdc0df57969e7049a9a37b0
//http://localhost:8080/weather?q=London&appid=c2e489273fdc0df57969e7049a9a37b0&cnt=3
//forecast for 16 days
//api.openweathermap.org/data/2.5/forecast/daily?q=London&cnt=3&appid=c2e489273fdc0df57969e7049a9a37b0

//5days
//api.openweathermap.org/data/2.5/forecast?q=London&appid=c2e489273fdc0df57969e7049a9a37b0