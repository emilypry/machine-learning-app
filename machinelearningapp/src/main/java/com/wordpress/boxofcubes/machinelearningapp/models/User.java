package com.wordpress.boxofcubes.machinelearningapp.models;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private List<Data> data;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User(){}
    public User(String username, String passwordHash){
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public int getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPasswordHash(){
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
    public List<Data> getData(){
        return data;
    }
    public void setData(List<Data> data){
        this.data = data;
    }
}
