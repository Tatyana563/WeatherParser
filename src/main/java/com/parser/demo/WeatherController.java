package com.parser.demo;

import com.parser.demo.dto.AvgTempResponse;
import com.parser.demo.dto.PrecipitationResponse;
import com.parser.demo.dto.WeatherResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

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
//http://localhost:8080//weather/findTopTemp?q=London&start=2020-08-30&finish=2020-09-06
    @GetMapping("/weather/findTopTemp")
//    @ResponseBody
    //TODO: заменить String на Instant
    public ResponseEntity<?> getForecast(@RequestParam("q") String city,
                                                      @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                      @RequestParam("finish") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date finishDate) {
        Optional<AvgTempResponse> avgTempResponseOptional = service.avgTempInCityBetweenTwoDates(city, startDate.toInstant(), finishDate.toInstant());
        if(avgTempResponseOptional.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        AvgTempResponse avgTempResponse = avgTempResponseOptional.get();
        System.out.println(avgTempResponse);
        return ResponseEntity.ok(avgTempResponse);


    }

    //http://localhost:8080//weather/findSum?q=London&start=2020-09-04
    @GetMapping("/weather/findSum")
    @ResponseBody
    public PrecipitationResponse getSumOfRain(@RequestParam("q") String city, @RequestParam("start") String startDate) {
        PrecipitationResponse precResponse = service.getSumOfPrecipitations(city, startDate);
        return precResponse;
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