package com.wordpress.boxofcubes.machinelearningapp.models;

public class Data {
    private int id;
    private String name;
    private double[] x;
    private double[] y;
    private int numPoints;
    private String datasetName;
    private String xLabel;
    private String yLabel;
    private String itemLabel;

    public Data(){}
    public Data(String name, double[] x, double[] y, String datasetName, String xLabel,
                String yLabel, String itemLabel){
        this.name = name;
        this.x = x;
        this.y = y;
        this.numPoints = x.length;
        this.datasetName = datasetName;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.itemLabel = itemLabel;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public double[] getX(){
        return x;
    }
    public void setX(double[] x){
        this.x = x;
    }
    public double[] getY(){
        return y;
    }
    public void setY(double[] y){
        this.y = y;
    }
    public int getNumPoints(){
        return numPoints;
    }
    public void setNumPoints(int numPoints){
        this.numPoints = numPoints;
    }
    public String getDatasetName(){
        return datasetName;
    }
    public void setDatasetName(String datasetName){
        this.datasetName = datasetName;
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
