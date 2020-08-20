package com.parser.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
//@Entity
public class WeatherDto {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
    @JsonProperty
    List<Weather> weather;
    @JsonProperty
    Main main;
}
