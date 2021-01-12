package com.wordpress.boxofcubes.machinelearningapp.models.dto;

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
}