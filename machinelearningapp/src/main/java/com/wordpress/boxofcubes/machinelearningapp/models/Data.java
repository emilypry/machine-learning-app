package com.wordpress.boxofcubes.machinelearningapp.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class Data {
    @Id
    @GeneratedValue
    private int id;
    
    /*private double[] x; // change to list 
    private double[] y;   */

    @OneToMany(mappedBy="data", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<DataValue> dataValues;
    /*private List<DataValue> x;
    private List<DataValue> y;*/

    private int numPoints;
    private String name;
    private String xLabel;
    private String yLabel;
    private String itemLabel;

    public Data(){}
    public Data(List<DataValue> dataValues, String name, String xLabel, String yLabel, String itemLabel){
        this.dataValues = dataValues;
        this.numPoints = dataValues.size();
        this.name = name;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.itemLabel = itemLabel;
    }
    /*public Data(List<DataValue> x, List<DataValue>  y, String name, String xLabel, String yLabel, String itemLabel){
        this.x = x;
        this.y = y;
        this.numPoints = x.size();
        this.name = name;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.itemLabel = itemLabel;
    }*/

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
    public List<DataValue> getDataValues(){
        return dataValues;
    }
    public void setDataValues(List<DataValue> dataValues){
        this.dataValues = dataValues;
    }
    /*public List<DataValue>  getY(){
        return y;
    }
    public void setY(List<DataValue>  y){
        this.y = y;
    }*/
    public int getNumPoints(){
        return numPoints;
    }
    public void setNumPoints(int numPoints){
        this.numPoints = numPoints;
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
