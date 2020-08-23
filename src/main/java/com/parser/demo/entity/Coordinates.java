package com.parser.demo.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Coordinates {
    private float lon;
    private float lat;

    public Coordinates() {
        //Empty
    }

    public Coordinates(float lon, float lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }
}
