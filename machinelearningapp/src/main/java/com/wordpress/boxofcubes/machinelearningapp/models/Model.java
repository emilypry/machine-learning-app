package com.wordpress.boxofcubes.machinelearningapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Model {
    @Id
    @GeneratedValue
    private int id;
    private double[] theta;
    private double trainingError;
    private double crossValError;
    private double testingError;
    @ManyToOne
    private Data data;

    public Model(double[] theta, double trainingError, double crossValError, double testingError){
        this.theta = theta;
        this.trainingError = trainingError;
        this.crossValError = crossValError;
        this.testingError = testingError;
    }

    public double[] getTheta(){
        return theta;
    }
    public void setTheta(double[] theta){
        this.theta = theta;
    }
    public double getTrainingError(){
        return trainingError;
    }
    public void setTrainingError(double trainingError){
        this.trainingError = trainingError;
    }
    public double getCrossValError(double crossValError){
        return crossValError;
    }
    public void setCrossValError(double crossValError){
        this.crossValError = crossValError;
    }
    public double getTestingError(){
        return testingError;
    }
    public void setTestingError(double testingError){
        this.testingError = testingError;
    }

}
