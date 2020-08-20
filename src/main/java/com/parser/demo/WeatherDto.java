package com.parser.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherDto {
    @JsonProperty
    List<Weather> weather;
    @JsonProperty
    Main main;
}
