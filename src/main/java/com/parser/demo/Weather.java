package com.parser.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {
    @JsonProperty
    private String main;
    @JsonProperty
    private String description;
}
