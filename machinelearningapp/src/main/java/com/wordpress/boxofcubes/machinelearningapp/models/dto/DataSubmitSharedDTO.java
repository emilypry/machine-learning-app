package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DataSubmitSharedDTO{
    @NotBlank(message="Name of dataset is missing")
    @Max(value=100, message="Name of dataset must be fewer than 100 characters")
    private String name;
    @NotBlank(message="Label for X data is missing")
    @Max(value=100, message="Label for X data must be fewer than 100 characters")
    private String xLabel;
    @NotBlank(message="Label for Y data is missing")
    @Max(value=100, message="Label for Y must be fewer than 100 characters")
    private String yLabel;
    @NotBlank(message="Label for item per example is missing")
    @Max(value=100, message="Label for item per example must be fewer than 100 characters")
    private String itemLabel;

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