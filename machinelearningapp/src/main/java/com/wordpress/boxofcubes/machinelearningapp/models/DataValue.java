package com.wordpress.boxofcubes.machinelearningapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DataValue{
    @Id
    @GeneratedValue
    private int id;
    private double value;
    @ManyToOne(cascade=CascadeType.PERSIST)
    private Data data;
    private boolean isX;

    public DataValue(double value, Data data, boolean isX){
        this.value = value;
        this.data = data;
        this.isX = isX;
    }

    public int getId(){
        return id;
    }
    public double getValue(){
        return value;
    }
    public void setValue(double value){
        this.value = value;
    }
    public Data getData(){
        return data;
    }
    public void setData(Data Data){
        this.data = Data;
    }
    public boolean getIsX(){
        return isX;
    }
    public void setIsX(boolean isX){
        this.isX = isX;
    }

}