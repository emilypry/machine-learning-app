package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import org.springframework.web.multipart.MultipartFile;

public class DataSubmissionDTO {
    private MultipartFile xFile;
    private MultipartFile yFile;
    private String xEntry;
    private String yEntry;
    private double[] rawX;
    private double[] rawY;
    private int numPoints;
    private String name;
    private String xLabel;
    private String yLabel;
    private String itemLabel;

    public MultipartFile getXFile(){
        return xFile;
    }
    public void setXFile(MultipartFile xFile){
        this.xFile = xFile;
    }
    public MultipartFile getYFile(){
        return yFile;
    }
    public void setYFile(MultipartFile yFile){
        this.yFile = yFile;
    }  
    public String getXEntry(){
        return xEntry;
    }
    public void setXEntry(String xEntry){
        this.xEntry = xEntry;
    }
    public String getYEntry(){
        return yEntry;
    }
    public void setYEntry(String yEntry){
        this.yEntry = yEntry;
    }  
    public double[] getRawX(){
        return rawX;
    }
    public void setRawX(double[] rawX){
        this.rawX = rawX;
    }
    public double[] getRawY(){
        return rawY;
    }
    public void setRawY(double[] rawY){
        this.rawY = rawY;
    }
    public int getNumPoints(){
        return numPoints;
    }
    public void setNumPoints(int numPoints){
        this.numPoints = numPoints;
    }
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
}