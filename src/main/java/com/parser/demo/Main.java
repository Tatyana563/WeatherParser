package com.parser.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Main {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty
    private Double temp;

    @JsonProperty
    private Integer pressure;

    @JsonProperty
    private Integer humidity;
}
