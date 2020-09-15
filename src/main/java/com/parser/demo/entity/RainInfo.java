package com.parser.demo.entity;

import org.apache.catalina.authenticator.SavedRequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RainInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double oneHour;
    private double threeHours;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
