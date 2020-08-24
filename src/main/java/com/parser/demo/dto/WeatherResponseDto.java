package com.parser.demo.dto;

import java.util.List;
import java.util.Set;

public class WeatherResponseDto {
    private int id;
    private String name;
    private CoordinatesDto coord;
    private Set<WeatherDto> weather;
    private MainDto main;
    private SysDto sys;
    private CloudDto clouds;
    private WindDto wind;
    private RainDto rain;
    private int timezone;
    private Long dt;

    public CloudDto getClouds() {
        return clouds;
    }

    public void setClouds(CloudDto clouds) {
        this.clouds = clouds;
    }

    public WindDto getWind() {
        return wind;
    }

    public void setWind(WindDto wind) {
        this.wind = wind;
    }

    public RainDto getRain() {
        return rain;
    }

    public void setRain(RainDto rain) {
        this.rain = rain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinatesDto getCoord() {
        return coord;
    }

    public void setCoord(CoordinatesDto coord) {
        this.coord = coord;
    }

    public Set<WeatherDto> getWeather() {
        return weather;
    }

    public void setWeather(Set<WeatherDto> weather) {
        this.weather = weather;
    }

    public MainDto getMain() {
        return main;
    }

    public void setMain(MainDto main) {
        this.main = main;
    }

    public SysDto getSys() {
        return sys;
    }

    public void setSys(SysDto sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }
}
