package com.wordpress.boxofcubes.machinelearningapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String passwordHash;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @OneToMany(mappedBy = "user")
    private List<Data> datasets;

    public User(){}
    public User(String username, String password){
        this.username = username;
        this.passwordHash = encoder.encode(password);
    }

    public boolean checkPassword(String password){
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
    public List<Data> getDatasets(){
        return datasets;
    }
    public void setDatasets(List<Data> datasets){
        this.datasets = datasets;
    }
}
