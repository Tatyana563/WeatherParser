package com.parser.demo.dto;

import java.util.List;

public class WeatherResponseDto {
    private int id;
    private String name;
    private CoordinatesDto coord;
    private List<WeatherDto> weather;
    private MainDto main;
    private SysDto sys;

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

    public List<WeatherDto> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDto> weather) {
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
}
