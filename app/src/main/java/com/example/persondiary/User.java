package com.example.persondiary;

import java.io.Serializable;

public class User implements Serializable {
     int userId;
     String username;
     String password;
     String gender;

    public User() {
    }

    public User(int userId, String username, String password, String gender) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
