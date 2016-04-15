package com.example.tripacker.tripacker.entity;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EILEENWEI on 4/13/16.
 */
public class TripEntity {
    @SerializedName("name")
    String name;
    @SerializedName("owner")
    String owner;
    @SerializedName("tip")
    String tip;
    @SerializedName("score")
    String score;
    @SerializedName("status")
    String status;
    @SerializedName("gmt_create")
    String gmt_create;
    @SerializedName("gmt_modified")
    String gmt_modified;


    // Make sure to always define this constructor with no arguments
    public TripEntity() {
        super();
    }

    // Parse model from JSON
    public TripEntity(JSONObject object){
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

    public static ArrayList<TripEntity> fromJson(JSONArray jsonArray) {
        ArrayList<TripEntity> trips = new ArrayList<TripEntity>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            TripEntity trip = new TripEntity(tweetJson);
         //   trip.save();
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



}
