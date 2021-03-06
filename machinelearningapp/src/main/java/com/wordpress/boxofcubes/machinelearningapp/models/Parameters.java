package com.wordpress.boxofcubes.machinelearningapp.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.wordpress.boxofcubes.machinelearningapp.models.dto.ParametersDTO;

public class Parameters {
    @GeneratedValue
    private int id;
    private double trainingProportion;
    private double[] initialTheta;
    private double alpha;
    private double lambda;
    private int maxIterations;
    private double convergenceLevel;

    public Parameters(){};
    public Parameters(double trainingProportion, double[] initialTheta, double alpha, double lambda, int maxIterations, double convergenceLevel){
        this.trainingProportion = trainingProportion;
        this.initialTheta = initialTheta;
        this.alpha = alpha;
        this.lambda = lambda;
        this.maxIterations = maxIterations;
        this.convergenceLevel = convergenceLevel;
    }
    public Parameters(ParametersDTO dto){
        trainingProportion = dto.getTrainingProportion();
        initialTheta = new double[]{dto.getTheta0(), dto.getTheta1()};
        alpha = dto.getAlpha();
        lambda = dto.getLambda();
        maxIterations = dto.getMaxIterations();
        convergenceLevel = dto.getConvergenceLevel();
    }

    public int getId(){
        return id;
    }
    public double getTrainingProportion(){
        return trainingProportion;
    }
    public void setTrainingProportion(double trainingProportion){
        this.trainingProportion = trainingProportion;
    }
    public double[] getInitialTheta(){
        return initialTheta;
    }
    public void setInitialTheta(double[] initialTheta){
        this.initialTheta = initialTheta;
    }
    public double getAlpha(){
        return alpha;
    }
    public void setAlpha(double alpha){
        this.alpha = alpha;
    }
    public double getLambda(){
        return lambda;
    }
    public void setLambda(double lambda){
        this.lambda = lambda;
    }
    public int getMaxIterations(){
        return maxIterations;
    }
    public void setMaxIterations(int maxIterations){
        this.maxIterations = maxIterations;
    }
    public double getConvergenceLevel(){
        return convergenceLevel;
    }
    public void setConvergenceLevel(double convergenceLevel){
        this.convergenceLevel = convergenceLevel;
    }
}
