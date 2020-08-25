package com.parser.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty
    private String main;

    @JsonProperty
    private String description;

    @JsonProperty
    @ManyToOne(cascade = {CascadeType.ALL})
    //@JoinColumn(name = "weather_dto_id")
    private WeatherDto weatherDto;


}

