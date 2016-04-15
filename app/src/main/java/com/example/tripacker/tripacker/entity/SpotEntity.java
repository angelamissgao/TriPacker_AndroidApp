package com.example.tripacker.tripacker.entity;


import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EILEENWEI on 4/13/16.
 */
public class SpotEntity {
    @SerializedName("name")
    String name;
    @SerializedName("category_id")
    String category_id;
    @SerializedName("city_id")
    String city_id;
    @SerializedName("geo_latitude")
    String geo_latitude;
    @SerializedName("geo_longitude")
    String geo_longitude;
    @SerializedName("image_main")
    String image_main;
    @SerializedName("image_rest")
    String image_rest;
    @SerializedName("score")
    String score;
    @SerializedName("status")
    String status;
    @SerializedName("gmt_create")
    String gmt_create;
    @SerializedName("gmt_modified")
    String gmt_modified;
    @SerializedName("gmt_approved")
    String gmt_approved;

    // Make sure to always define this constructor with no arguments
    public SpotEntity() {
        super();
    }

    // Parse model from JSON
    public SpotEntity(JSONObject object){
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
    }

    public static ArrayList<SpotEntity> fromJson(JSONArray jsonArray) {
        ArrayList<SpotEntity> spots = new ArrayList<SpotEntity>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject spotJson = null;
            try {
                spotJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            SpotEntity spot = new SpotEntity(spotJson);
            //spot.save();
            spots.add(spot);
        }

        return spots;
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



}
