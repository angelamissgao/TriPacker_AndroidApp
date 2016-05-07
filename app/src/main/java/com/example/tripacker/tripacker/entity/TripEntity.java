package com.example.tripacker.tripacker.entity;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Trip Entity used in the data layer.
 */
public class TripEntity implements Serializable {
    @SerializedName("name")
    String name;
    @SerializedName("owner")
    String owner;
    @SerializedName("cover_photo")
    String cover_photo;
    @SerializedName("cover_photo_id")
    String cover_photo_id;
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
    @SerializedName("duration")
    String duration;
    @SerializedName("trip_id")
    int trip_id;
    @SerializedName("beginDate")
    String beginDate;
    @SerializedName("endDate")
    String endDate;
    @SerializedName("ownerId")
    int ownerId;
    @SerializedName("ownerNickname")
    String ownerNickname;
    @SerializedName("spots")
    ArrayList<SpotEntity> spots;

    Integer image_local;


    // Make sure to always define this constructor with no arguments
    public TripEntity() {
        super();
    }

    public TripEntity(String name, String cover_photo_url){
        this.name = name;
        this.cover_photo = cover_photo_url;
    }
    public TripEntity(String name, int cover_photo_id){
        this.name = name;
        this.cover_photo_id = cover_photo_id+"";
    }
    // Parse model from JSON
    public TripEntity(JSONObject object){
        super();

        try {
            this.name = object.getString("tripName");
            if(object.has("tripId")){
                this.trip_id = object.getInt("tripId");
            }
     /*       this.owner = object.getString("owner");
            this.cover_photo = object.getString("cover_photo");
            this.tip = object.getString("tip");
            this.status = object.getString("status");
            this.score = object.getString("score");*/
            if(object.has(gmt_create)){
                this.gmt_create = object.getString("gmt_create");
            }
          //  this.gmt_modified = object.getString("gmt_modified");
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
    public String getCover_photo(){return cover_photo;}
    public int getCover_photo_id(){return Integer.parseInt(cover_photo_id);}
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
    public String getDuration() { return duration;}
    public int getTrip_id() { return trip_id; }
    public String getBeginDate() { return beginDate; }
    public String getEndDate() { return endDate;}
    public int getOwnerId() { return ownerId; }
    public ArrayList<SpotEntity> getSpots() { return spots; }
    public Integer getImage_local() { return image_local; }
    public String getOwnerNickname() { return ownerNickname; }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setEstimateDuration(String time) {this.duration = time; }
    public void setTrip_id(int id) {this.trip_id = id;}
    public void setCover_photo(String cover_photo) {this.cover_photo = cover_photo;}
    public void SetGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }
    public void setBeginDate(String beginDate) {this.beginDate = beginDate;}
    public void setEndDate(String endDate) {this.endDate = endDate;}
    public void setOwnerId(int ownerId) { this.ownerId = ownerId;}
    public void setOwnername(String ownername) {this.ownerNickname = ownername;}
    public void setSpots(ArrayList<SpotEntity> spots) {this.spots = spots; }
    public void setImage_local(Integer image_local) {this.image_local = image_local; }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("***** Trip Entity Details *****\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("tripID=" + this.getTrip_id() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

}
