package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import com.wordpress.boxofcubes.machinelearningapp.models.Parameters;

public class ParametersDTO extends Parameters{
    private double theta0;
    private double theta1;
    private int numPointsInData;

    public ParametersDTO(){};

    public static ParametersDTO getDefaultParameters(){
        ParametersDTO p = new ParametersDTO();
        p.setTrainingProportion(.6);
        p.setTheta0(0);
        p.setTheta1(0);
        p.setAlpha(.01);
        p.setLambda(0);
        p.setMaxIterations(500);
        p.setConvergenceLevel(.001);

        return p;
    }

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
    public int getNumPointsInData(){
        return numPointsInData;
    }
    public void setNumPointsInData(int numPointsInData){
        this.numPointsInData = numPointsInData;
    }
}
