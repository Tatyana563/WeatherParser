package com.parser.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Main {
    @JsonProperty
    private Double temp;
    @JsonProperty
    private Integer pressure;
    @JsonProperty
    private Integer humidity;
}
