package com.example.tripacker.tripacker.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

@Table(name = "Trips")
public class Trip extends Model {
    // Define database columns and associated fields

    @Column(name = "name")
    String name;
    @Column(name = "owner")
    String owner;
    @Column(name = "tip")
    String tip;
    @Column(name = "score")
    String score;
    @Column(name = "status")
    String status;
    @Column(name = "gmt_create")
    String gmt_create;
    @Column(name = "gmt_modified")
    String gmt_modified;


    // Make sure to always define this constructor with no arguments
    public Trip() {
        super();
    }

    // Parse model from JSON
    public Trip(JSONObject object){
        super();

        try {
            this.name = object.getString("name");
            this.owner = object.getString("owner");
            this.tip = object.getString("tip");
            this.status = object.getString("status");
            this.score = object.getString("score");
            this.gmt_create = object.getString("gmt_create");
            this.gmt_modified = object.getString("gmt_modified");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Trip> fromJson(JSONArray jsonArray) {
        ArrayList<Trip> trips = new ArrayList<Trip>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Trip trip = new Trip(tweetJson);
            trip.save();
            trips.add(trip);
        }

        return trips;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getOwner() {
        return owner;
    }
    public String getTip() {
        return tip;
    }
    public String getStatus() {
        return status;
    }
    public String getScore() {
        return score;
    }
    public String getGmt_create() {
        return gmt_create;
    }
    public String getGmt_modified() {
        return gmt_modified;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void SetGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }


    // Record Finders
    public static Trip byId(long id) {
        return new Select().from(Trip.class).where("id = ?", id).executeSingle();
    }
    public static List<Trip> recentTrips() {
        return new Select().from(Trip.class).orderBy("id DESC").limit("300").execute();
    }


}
