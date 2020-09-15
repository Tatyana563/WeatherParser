package com.parser.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainDto {
    @JsonProperty("1h")
    private double oneHour;
    @JsonProperty("3h")
    private double threeHours;

    public double getOneHour() {
        return oneHour;
    }

    public void setOneHour(double oneHour) {
        this.oneHour = oneHour;
    }

    public double getThreeHours() {
        return threeHours;
    }

    public void setThreeHours(double threeHours) {
        this.threeHours = threeHours;
    }

    public RainDto(double oneHour, double threeHours) {
        this.oneHour = oneHour;
        this.threeHours = threeHours;
    }

    public RainDto() {
    }
}
