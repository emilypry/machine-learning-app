package com.wordpress.boxofcubes.machinelearningapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String passwordHash;
    //private List<Data> data;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User(){}
    public User(String username, String password){
        this.username = username;
        this.passwordHash = encoder.encode(password);
    }

    public boolean correctPassword(String password){
        return encoder.matches(password, passwordHash);
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
    /*public List<Data> getData(){
        return data;
    }
    public void setData(List<Data> data){
        this.data = data;
    }*/
}
