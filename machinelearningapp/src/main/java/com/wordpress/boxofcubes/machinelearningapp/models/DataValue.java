package com.wordpress.boxofcubes.machinelearningapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DataValue{
    @Id
    @GeneratedValue
    private int id;
    private double value;

    private int listId;

    public double getValue(){
        return value;
    }
    public void setValue(double value){
        this.value = value;
    }
    public int getListId(){
        return listId;
    }
}