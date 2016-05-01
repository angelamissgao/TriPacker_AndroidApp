package com.example.tripacker.tripacker.entity;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User Entity used in the data layer.
 */

public class UserProfileEntity {


    @SerializedName("uid")
    String uid;
    @SerializedName("selfie")
    String selfie;
    @SerializedName("username")
    String username;
    @SerializedName("nickname")
    String nickname;
    @SerializedName("totalTrips")
    String totalTrips;
    @SerializedName("totalFollowers")
    String totalFollowers;
    @SerializedName("totalFollowings")
    String totalFollowings;
    @SerializedName("gmt_create")
    String gmt_create;


    public UserProfileEntity() {
        //empty
    }
    //follower
    public UserProfileEntity(String uid, String username, String nickname) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
    }

    // Parse model from JSON
    public UserProfileEntity(JSONObject object){
        super();

        try {
            this.username = object.getString("username");
            this.nickname = object.getString("nickname");
            this.gmt_create = object.getString("gmt_create");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getUserId() {
        return uid;
    }
    public String getNickname() {
        return nickname;
    }
    public String getUsername() {
        return username;
    }
    public String getTotalTrips(){
        return totalTrips;
    }
    public String getTotalFollowers(){
        return totalFollowers;
    }
    public String getTotalFollowings(){
        return totalFollowings;
    }
    public String getGmt_create() {
        return gmt_create;
    }

    // Setters
    public void setUserId(String id) {
        this.uid = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setTotalTrips(String totalTrips) {
        this.totalTrips = totalTrips;
    }
    public void setTotalFollowers(String totalFollowers) {
        this.totalFollowers = totalFollowers;
    }
    public void setTotalFollowings(String totalFollowings) {
        this.totalFollowings = totalFollowings;
    }
    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("***** User Profile Entity Details *****\n");
        stringBuilder.append("id=" + this.getUserId() + "\n");
        stringBuilder.append("username=" + this.getUsername() + "\n");
        stringBuilder.append("nickname=" + this.getNickname() + "\n");
        stringBuilder.append("totalTrips=" + this.getTotalTrips() + "\n");
        stringBuilder.append("totalFollowers=" + this.getTotalFollowers() + "\n");
        stringBuilder.append("totalFollowings=" + this.getTotalFollowings() + "\n");
        stringBuilder.append("created_at=" + this.getGmt_create() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

}