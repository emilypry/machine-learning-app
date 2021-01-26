package com.wordpress.boxofcubes.machinelearningapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SavingModel {
    @Id
    @GeneratedValue
    private int id;
    private double theta0;
    private double theta1;
    private double trainingError;
    private double crossValError;
    private double testingError;
    @ManyToOne
    private Data data;

    public SavingModel(){}
    public SavingModel(double theta0, double theta1, double trainingError, double crossValError, double testingError){
        this.theta0 = theta0;
        this.theta1 = theta1;
        this.trainingError = trainingError;
        this.crossValError = crossValError;
        this.testingError = testingError;
    }

    public SavingModel(LinearRegression lr){
        theta0 = lr.getTrainedTheta()[0];
        theta1 = lr.getTrainedTheta()[1];
        trainingError = lr.getTrainingError();
        crossValError = lr.getCrossValError();
        testingError = lr.getTestingError();
    }

    public int getId(){
        return id;
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
    public Data getData(){
        return data;
    }
    public void setData(Data data){
        this.data = data;
    }

}
