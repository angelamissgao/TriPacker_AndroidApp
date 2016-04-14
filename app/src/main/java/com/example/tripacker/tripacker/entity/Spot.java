package com.example.tripacker.tripacker.entity;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Spots")
public class Spot extends Model {
    // Define database columns and associated fields

    @Column(name = "name")
    String name;
    @Column(name = "category_id")
    String category_id;
    @Column(name = "city_id")
    String city_id;
    @Column(name = "geo_latitude")
    String geo_latitude;
    @Column(name = "geo_longitude")
    String geo_longitude;
    @Column(name = "image_main")
    String image_main;
    @Column(name = "image_rest")
    String image_rest;
    @Column(name = "score")
    String score;
    @Column(name = "status")
    String status;
    @Column(name = "gmt_create")
    String gmt_create;
    @Column(name = "gmt_modified")
    String gmt_modified;
    @Column(name = "gmt_approved")
    String gmt_approved;

    // Make sure to always define this constructor with no arguments
    public Spot() {
        super();
    }

    // Parse model from JSON
    public Spot(JSONObject object){
        super();

        try {
            this.name = object.getString("name");
            this.category_id = object.getString("category_id");
            this.city_id = object.getString("city_id");
            this.geo_latitude = object.getString("geo_latitude");
            this.geo_longitude = object.getString("geo_longitude");
            this.image_main = object.getString("image_main");
            this.image_rest = object.getString("image_rest");
            this.status = object.getString("status");
            this.score = object.getString("score");
            this.gmt_create = object.getString("gmt_create");
            this.gmt_modified = object.getString("gmt_modified");
            this.gmt_approved = object.getString("gmt_approved");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("new Spot model created", "----->");
    }

    public static ArrayList<Spot> fromJson(JSONArray jsonArray) {
        ArrayList<Spot> trips = new ArrayList<Spot>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Spot trip = new Spot(tweetJson);
            trip.save();
            trips.add(trip);
        }

        return trips;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getCategory_id() {
        return category_id;
    }
    public String getCity_id() {
        return city_id;
    }
    public String getGeo_latitude(){ return  geo_latitude;}
    public String getGeo_longitude(){ return  geo_longitude;}
    public String getImage_main(){ return  image_main;}
    public String getImage_rest(){ return  image_rest;}
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
    public String getGmt_approved() {
        return gmt_approved;
    }

    //Setters
    public void setName(String name) { this.name = name; }

    // Record Finders
    public static Spot byId(long id) {
        return new Select().from(Spot.class).where("id = ?", id).executeSingle();
    }
    public static List<Spot> recentSpots() {
        return new Select().from(Spot.class).orderBy("id DESC").limit("300").execute();
    }


}
