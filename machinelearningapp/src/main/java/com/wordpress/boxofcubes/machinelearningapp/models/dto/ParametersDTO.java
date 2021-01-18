package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import com.wordpress.boxofcubes.machinelearningapp.models.Parameters;

public class ParametersDTO extends Parameters{
    private double theta1;
    private double theta2;

    public double getTheta1(){
        return theta1;
    }
    public void setTheta1(double theta1){
        this.theta1 = theta1;
    }
    public double getTheta2(){
        return theta2;
    }
    public void setTheta2(double theta2){
        this.theta2 = theta2;
    }
}
