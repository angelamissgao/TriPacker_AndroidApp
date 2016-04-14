package com.example.tripacker.tripacker.entity;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Members")
public class User extends Model {
    // Define database columns and associated fields

    @Column(name = "username")
    String username;
    @Column(name = "email")
    String email;
    @Column(name = "tel")
    String tel;
    @Column(name = "status")
    String status;
    @Column(name = "grade")
    String grade;
    @Column(name = "gmt_create")
    String gmt_create;
    @Column(name = "gmt_modified")
    String gmt_modified;
    @Column(name = "gmt_last_login")
    String gmt_last_login;

    // Make sure to always define this constructor with no arguments
    public User() {
        super();
    }

    // Parse model from JSON
    public User(JSONObject object){
        super();

        try {
            this.username = object.getString("username");
            this.email = object.getString("email");
            this.tel = object.getString("tel");
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
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getTel() {
        return tel;
    }
    public String getStatus() {
        return status;
    }
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

    // Record Finders
    public static User byId(long id) {
        return new Select().from(User.class).where("id = ?", id).executeSingle();
    }


}
