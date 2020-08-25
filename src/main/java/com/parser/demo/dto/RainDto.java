package com.parser.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainDto {
    @JsonProperty("1h")
    private String oneHour;
    @JsonProperty("3h")
    private String threeHours;

    public String getOneHour() {
        return oneHour;
    }

    public void setOneHour(String oneHour) {
        this.oneHour = oneHour;
    }

    public String getThreeHours() {
        return threeHours;
    }

    public void setThreeHours(String threeHours) {
        this.threeHours = threeHours;
    }
}
