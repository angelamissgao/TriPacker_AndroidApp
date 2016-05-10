package com.example.tripacker.tripacker.entity;


import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Spot Entity used in the data layer.
 */
public class SpotEntity implements Serializable {

    @SerializedName("name")
    String name;
    @SerializedName("spotId")
    String spotId;
    @SerializedName("address")
    String address;
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
    @SerializedName("description")
    String description;

    Integer image_local;

    // Make sure to always define this constructor with no arguments
    public SpotEntity() {
        super();
    }

    // Parse model from JSON
    public SpotEntity(JSONObject object) {
        super();

        try {
            this.name = object.getString("spotName");
            this.spotId = object.getString("spotId");
//            this.category_id = object.getString("category_id");
//            this.city_id = object.getString("city_id");
            if (object.has("geoLatitude")) {
                this.geo_latitude = object.getString("geoLatitude");
            }
            if (object.has("geoLongitude")) {
                this.geo_longitude = object.getString("geoLongitude");
            }
//            this.image_main = object.getString("image_main");
//            this.image_rest = object.getString("image_rest");
//            this.status = object.getString("status");
//            this.score = object.getString("score");
//            this.gmt_create = object.getString("gmt_create");
//            this.gmt_modified = object.getString("gmt_modified");
//            this.gmt_approved = object.getString("gmt_approved");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<SpotEntity> fromJson(JSONArray jsonArray) {
        ArrayList<SpotEntity> spots = new ArrayList<SpotEntity>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
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

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String id) {
        this.spotId = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getGeo_latitude() {
        return geo_latitude;
    }

    public void setGeo_latitude(String latitude) {
        this.geo_latitude = latitude;
    }

    public String getGeo_longitude() {
        return geo_longitude;
    }

    public void setGeo_longitude(String longitude) {
        this.geo_longitude = longitude;
    }

    public String getImage_main() {
        return image_main;
    }

    public String getImage_rest() {
        return image_rest;
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

    public String getGmt_approved() {
        return gmt_approved;
    }

    public Integer getImage_local() {
        return image_local;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_source_local(int postion) {
        this.image_local = postion;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("***** Spot Entity Details *****\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("spotID=" + this.getSpotId() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

}
