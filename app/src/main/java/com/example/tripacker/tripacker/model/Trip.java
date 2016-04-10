package com.example.tripacker.tripacker.model;

/**
 * Created by EILEENWEI on 4/9/16.
 */
public class Trip {
    private String date;
    private String name;
    private String location;

    public Trip(String name, String date, String location){
        this.name = name;
        this.date = date;
        this.location = location;
    }
    public String getDate(){
        return date;
    }
    public String getName(){
        return name;
    }
}
