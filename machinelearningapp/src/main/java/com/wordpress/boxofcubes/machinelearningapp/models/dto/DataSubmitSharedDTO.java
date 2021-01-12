package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.validation.constraints.NotNull;

public class DataSubmitSharedDTO{

    @NotNull(message="Name of dataset is missing")
    private String name;
    @NotNull(message="Label for X data is missing")
    private String xLabel;
    @NotNull(message="Label for Y data is missing")
    private String yLabel;
    @NotNull(message="Label for item per example is missing")
    private String itemLabel;

    public DataSubmitSharedDTO(){}

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getXLabel(){
        return xLabel;
    }
    public void setXLabel(String xLabel){
        this.xLabel = xLabel;
    }
    public String getYLabel(){
        return yLabel;
    }
    public void setYLabel(String yLabel){
        this.yLabel = yLabel;
    }
    public String getItemLabel(){
        return itemLabel;
    }
    public void setItemLabel(String itemLabel){
        this.itemLabel = itemLabel;
    }

    /** Reads a file and returns the list of doubles in the file. */
    public static double[] convertToNums(File file) throws FileNotFoundException, InputMismatchException, IOException{
        ArrayList<Double> numbers = new ArrayList<>();
        Scanner scan = new Scanner(file);
        while(scan.hasNext()){
            numbers.add(scan.nextDouble());
        }
        scan.close();
      
        double[] list = new double[numbers.size()];
        for(int i=0; i<numbers.size(); i++){
            list[i] = numbers.get(i);
        }
        return list;
    }
}