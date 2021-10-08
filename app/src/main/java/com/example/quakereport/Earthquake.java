package com.example.quakereport;

public class Earthquake {
    private double mag;
    private String place;
    private long unixDate;
    private String url;
    public Earthquake(double mag, String place, long unixDate , String url){
        this.mag=mag;
        this.place=place;
        this.unixDate=unixDate;
        this.url=url;
    }

    public String getPlace() {
        return place;
    }

    public double getMag() {
        return mag;
    }

    public long getUnixDate() {
        return unixDate;
    }

    public String getUrl() {
        return url;
    }
}
