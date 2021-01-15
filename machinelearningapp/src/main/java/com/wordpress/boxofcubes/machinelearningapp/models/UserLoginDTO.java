package com.wordpress.boxofcubes.machinelearningapp.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserLoginDTO {

    // check if that username exists!!!!!!!!!
    @NotBlank(message="Please enter your username")
    private String username;

    // check if that's the right password!!!!!!
    @NotBlank(message="Please enter your password")
    private String password;

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
