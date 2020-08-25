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

    @OneToOne(cascade = CascadeType.ALL)
    private MainInfo mainInfo;

    @ManyToOne
    private DailyInfo dailyInfo;


    @ManyToOne
    private City city;

    @OneToOne(cascade = CascadeType.ALL)
    private RainInfo rainInfo;

    @OneToOne(cascade = CascadeType.ALL)
    private WindInfo windInfo;

    @OneToOne(cascade = CascadeType.ALL)
    private CloudInfo cloudInfo;

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

    public RainInfo getRainInfo() {
        return rainInfo;
    }

    public void setRainInfo(RainInfo rainInfo) {
        this.rainInfo = rainInfo;
    }

    public WindInfo getWindInfo() {
        return windInfo;
    }

    public void setWindInfo(WindInfo windInfo) {
        this.windInfo = windInfo;
    }

    public CloudInfo getCloudInfo() {
        return cloudInfo;
    }

    public void setCloudInfo(CloudInfo cloudInfo) {
        this.cloudInfo = cloudInfo;
    }
}
