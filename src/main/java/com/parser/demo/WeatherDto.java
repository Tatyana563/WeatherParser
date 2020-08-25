package com.parser.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class WeatherDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty
    @OneToMany(fetch = FetchType.LAZY,  mappedBy = "weatherDto",cascade = {CascadeType.ALL})
    private List<Weather> weather =new ArrayList<>();


    @JsonProperty
    @OneToOne(cascade = {CascadeType.ALL})
    Main main;

    @JsonProperty
    @OneToOne(cascade = {CascadeType.ALL})
    GeneralInfo sys;

    @JsonProperty("name")
    private String name;

}
