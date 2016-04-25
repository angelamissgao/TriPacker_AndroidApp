package com.example.tripacker.tripacker.entity;

import com.google.gson.annotations.SerializedName;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User Entity used in the data layer.
 */

public class UserEntity {


    @SerializedName("id")
    String id;
    @SerializedName("username")
    String username;
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

    // Parse model from JSON
    public UserEntity(JSONObject object){
        super();

        try {
            this.username = object.getString("username");
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
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getTel() {
        return tel;
    }
    public String getBirthday() {return birthday;}
    public String getGender() {return gender;}
    public String getStatus() {
        return status;
    }
    public String getIntroduction(){ return  introduction;}
    public String getGrade() {
        return grade;
    }
    public String getGmt_create() {
        return gmt_create;
    }
    public String getGmt_modified() {
        return gmt_modified;
    }
    public String getGmt_last_login() {
        return gmt_last_login;
    }

    // Setters
    public void setUserId(String id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setBirthday(String birthday) {this.birthday = birthday;}
    public void setIntroduction(String introduction) {this.introduction = introduction;}
    public void setGender(String gender){ this.gender = gender; }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }
    public void setGmt_modified(String gmt_modified) {
        this.gmt_modified = gmt_modified;
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
