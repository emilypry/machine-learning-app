package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;

import org.springframework.web.multipart.MultipartFile;

public class DataSubmissionDTO extends Data{
    private MultipartFile xFile;
    private MultipartFile yFile;
    private String xEntry;
    private String yEntry;
    private double[] rawX;
    private double[] rawY;

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
}