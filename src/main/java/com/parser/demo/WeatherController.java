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
    RestTemplate restTemplate;

    @RequestMapping("/weather")
//   @GetMapping("/weather")
//   @ResponseBody
    public WeatherDto getForecast(/*@RequestParam("q") String city, @RequestParam("appid") String appId*/) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
//       String input = String.format("api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, appId);
//        return restTemplate.exchange(input,
//                HttpMethod.GET, entity, WeatherDto.class).getBody();
       return restTemplate.exchange("api.openweathermap.org/data/2.5/weather?q=London&appid=c2e489273fdc0df57969e7049a9a37b0",
               HttpMethod.GET, entity, WeatherDto.class).getBody();
    }
}
//Working url
//api.openweathermap.org/data/2.5/weather?q=London&appid=c2e489273fdc0df57969e7049a9a37b0
//myUrl
//http://localhost:8080/weather?q=London&appid=c2e489273fdc0df57969e7049a9a37b0