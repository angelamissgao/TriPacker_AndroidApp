package com.example.tripacker.tripacker.entity;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * User Entity used in the data layer.
 */

public class UserEntity implements Serializable {


    @SerializedName("uid")
    String uid;
    @SerializedName("username")
    String username;
    @SerializedName("nickname")
    String nickname;
    @SerializedName("gender")
    String gender;
    @SerializedName("email")
    String email;
    @SerializedName("tel")
    String tel;
    @SerializedName("birthday")
    String birthday;
    @SerializedName("introduction")
    String introduction;
    @SerializedName("status")
    String status;
    @SerializedName("grade")
    String grade;
    @SerializedName("gmt_create")
    String gmt_create;
    @SerializedName("gmt_modified")
    String gmt_modified;
    @SerializedName("gmt_last_login")
    String gmt_last_login;

    public UserEntity() {
        //empty
    }

    //follower
    public UserEntity(String uid, String username, String nickname) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
    }

    // Parse model from JSON
    public UserEntity(JSONObject object) {
        super();

        try {
            this.username = object.getString("username");
            this.nickname = object.getString("nickname");
            this.email = object.getString("email");
            this.tel = object.getString("tel");
            this.birthday = object.getString("birthday");
            this.introduction = object.getString("introduction");
            this.gender = object.getString("gender");
            this.status = object.getString("status");
            this.grade = object.getString("grade");
            this.gmt_create = object.getString("gmt_create");
            this.gmt_modified = object.getString("gmt_modified");
            this.gmt_last_login = object.getString("gmt_last_login");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getUserId() {
        return uid;
    }

    // Setters
    public void setUserId(String id) {
        this.uid = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(String gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public String getGmt_last_login() {
        return gmt_last_login;
    }

    public void setGmt_last_login(String gmt_last_login) {
        this.gmt_last_login = gmt_last_login;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("***** User Entity Details *****\n");
        stringBuilder.append("id=" + this.getUserId() + "\n");
        stringBuilder.append("username=" + this.getUsername() + "\n");
        stringBuilder.append("nickname=" + this.getNickname() + "\n");
        stringBuilder.append("email=" + this.getEmail() + "\n");
        stringBuilder.append("tel=" + this.getTel() + "\n");
        stringBuilder.append("birthday=" + this.getBirthday() + "\n");
        stringBuilder.append("gender=" + this.getGender() + "\n");
        stringBuilder.append("introduction=" + this.getIntroduction() + "\n");
        stringBuilder.append("status=" + this.getStatus() + "\n");
        stringBuilder.append("grade=" + this.getGrade() + "\n");
        stringBuilder.append("created_at=" + this.getGmt_create() + "\n");
        stringBuilder.append("updated_at=" + this.getGmt_modified() + "\n");
        stringBuilder.append("last_login=" + this.getGmt_last_login() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

}
