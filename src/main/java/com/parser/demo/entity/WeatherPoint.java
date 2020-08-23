package com.parser.demo.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
public class WeatherPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Instant date;

    @OneToMany
    @JoinTable
    private Set<WeatherCondition> conditions;//no List for Hibernate

    @OneToOne
    private MainInfo mainInfo;

    @ManyToOne
    private DailyInfo dailyInfo;

//OneToOne +rAIN, WIND, CLOUDS
    @ManyToOne
    private City city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<WeatherCondition> getConditions() {
        return conditions;
    }

    public void setConditions(Set<WeatherCondition> conditions) {
        this.conditions = conditions;
    }

    public MainInfo getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(MainInfo mainInfo) {
        this.mainInfo = mainInfo;
    }

    public DailyInfo getDailyInfo() {
        return dailyInfo;
    }

    public void setDailyInfo(DailyInfo dailyInfo) {
        this.dailyInfo = dailyInfo;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
