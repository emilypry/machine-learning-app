package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import com.wordpress.boxofcubes.machinelearningapp.models.Parameters;

public class ParametersDTO extends Parameters{
    private double theta0;
    private double theta1;

    public double getTheta0(){
        return theta0;
    }
    public void setTheta0(double theta0){
        this.theta0 = theta0;
    }
    public double getTheta1(){
        return theta1;
    }
    public void setTheta1(double theta1){
        this.theta1 = theta1;
    }
}
