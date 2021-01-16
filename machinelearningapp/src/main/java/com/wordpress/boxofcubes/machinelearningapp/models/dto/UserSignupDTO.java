package com.wordpress.boxofcubes.machinelearningapp.models.dto;

public class UserSignupDTO extends UserLoginDTO{
    private String verifyPassword;

    public String getVerifyPassword(){
        return verifyPassword;
    }
    public void setVerifyPassword(String verifyPassword){
        this.verifyPassword = verifyPassword;
    }
}
