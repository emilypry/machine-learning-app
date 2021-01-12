package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class DataFormDTO{
    @NotNull(message="X data file is missing")
    private MultipartFile xFile;
    @NotNull(message="Y data file is missing")
    private MultipartFile yFile;
    @NotNull(message="Name of dataset is missing")
    private String name;
    @NotNull(message="Label for X data is missing")
    private String xLabel;
    @NotNull(message="Label for Y data is missing")
    private String yLabel;
    @NotNull(message="Label for item per example is missing")
    private String itemLabel;

    public DataFormDTO(){}
    public DataFormDTO(MultipartFile xFile, MultipartFile yFile, String name, String xLabel, String yLabel, String itemLabel){
        this.xFile = xFile;
        this.yFile = yFile;
        this.name = name;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.itemLabel = itemLabel;
    }

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