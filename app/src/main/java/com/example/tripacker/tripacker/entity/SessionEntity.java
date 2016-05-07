package com.example.tripacker.tripacker.entity;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Session Entity used in the data layer.
 */

public class SessionEntity {

    @SerializedName("user_id")
    String user_id;
    @SerializedName("cookie")
    String cookie;
    @SerializedName("user_email")
    String user_email;
    @SerializedName("user_name")
    String user_name;
    @SerializedName("gmt_create")
    String gmt_create;

    public SessionEntity() {
        //empty
    }

    // Parse model from JSON
    public SessionEntity(JSONObject object){
        super();

        try {
            this.user_name = object.getString("user_name");
            this.user_id = object.getString("user_id");
            this.cookie = object.getString("cookie");
            this.gmt_create = object.getString("gmt_create");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getUsername() {
        return user_name;
    }
    public String getUserEmail() {
        return user_email;
    }
    public String getUserId() {
        return user_id;
    }
    public String getCookie() {
        return cookie;
    }
    public String getGmt_create() {
        return gmt_create;
    }
    // Setters
    public void setUserId(String id) {
        this.user_id = user_id;
    }
    public void setUsername(String user_name) {
        this.user_name = user_name;
    }
    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("***** Session Entity Details *****\n");
        stringBuilder.append("user_id=" + this.getUserId() + "\n");
        stringBuilder.append("user_name=" + this.getUsername() + "\n");
        stringBuilder.append("user_email=" + this.getUserEmail() + "\n");
        stringBuilder.append("Cookie=" + this.getCookie() + "\n");
        stringBuilder.append("created_at=" + this.getGmt_create() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

}
