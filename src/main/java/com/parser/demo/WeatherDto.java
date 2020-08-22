package com.parser.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
@Entity
public class WeatherDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty
    @OneToMany(cascade = CascadeType.ALL)
    List<Weather> weather;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty
    Main main;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty
   GeneralInfo sys;
}
