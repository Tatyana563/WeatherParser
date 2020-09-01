package com.parser.demo.dto;

public class PrecipitationResponse {
    String name;
    double precipitationSum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrecipitationSum() {
        return precipitationSum;
    }

    public void setPrecipitationSum(double precipitationSum) {
        this.precipitationSum = precipitationSum;
    }

    public PrecipitationResponse(String name, double precipitationSum) {
        this.name = name;
        this.precipitationSum = precipitationSum;
    }

    public PrecipitationResponse() {
    }
}
