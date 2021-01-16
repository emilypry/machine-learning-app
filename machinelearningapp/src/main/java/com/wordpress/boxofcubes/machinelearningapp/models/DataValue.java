package com.wordpress.boxofcubes.machinelearningapp.models;

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
    @ManyToOne
    private Data dataset;

    public DataValue(double value, Data dataset){
        this.value = value;
        this.dataset = dataset;
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
    public Data getDataset(){
        return dataset;
    }
    public void setDataset(Data dataset){
        this.dataset = dataset;
    }
}