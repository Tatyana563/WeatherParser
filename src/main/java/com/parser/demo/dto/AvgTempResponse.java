package com.parser.demo.dto;

public class AvgTempResponse {
    private double avgTemp;
    private String city;

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AvgTempResponse(double avgTemp, String city) {
        this.avgTemp = avgTemp;
        this.city = city;
    }
}
