package com.parser.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class GeneralInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonProperty
    private String country;
    @JsonProperty
    private String sunrise;
    @JsonProperty
    private String sunset;

//@OneToOne
//    WeatherDto weatherDto;
}
