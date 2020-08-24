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
    private String oneHour;
    private String threeHours;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
