package com.parser.demo.dto;

public class PrecipitationResponse {
    String name;
    Double precipitationSum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrecipitationSum() {
        return precipitationSum;
    }

    public void setPrecipitationSum(Double precipitationSum) {
        this.precipitationSum = precipitationSum;
    }

    public PrecipitationResponse(String name, Double precipitationSum) {
        this.name = name;
        this.precipitationSum = precipitationSum;
    }

    public PrecipitationResponse() {
    }

}
