package com.parser.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CloudInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double percentageOfClouds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPercentageOfClouds() {
        return percentageOfClouds;
    }

    public void setPercentageOfClouds(double percentageOfClouds) {
        this.percentageOfClouds = percentageOfClouds;
    }
}
