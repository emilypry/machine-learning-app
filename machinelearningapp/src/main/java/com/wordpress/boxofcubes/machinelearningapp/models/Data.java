package com.wordpress.boxofcubes.machinelearningapp.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Data {
    @Id
    @GeneratedValue
    private int id;

    @OneToMany(mappedBy="data", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<DataValue> dataValues;

    private int numPoints;
    private String name;
    private String xLabel;
    private String yLabel;
    private String itemLabel;

    @ManyToOne
    private User user;

    public Data(){}
    public Data(List<DataValue> dataValues){
        this.dataValues = dataValues;
        this.numPoints = this.dataValues.size() / 2;
    }
    public Data(List<DataValue> dataValues, String name, String xLabel, String yLabel, String itemLabel){
        this.dataValues = dataValues;
        this.numPoints = dataValues.size();
        this.name = name;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.itemLabel = itemLabel;
    }


    public double[] getX(){
        List<DataValue> xList = new ArrayList<>();
        for(DataValue d : dataValues){
            if(d.getIsX() == true){
                xList.add(d);
            }
        }
        double[] x = new double[numPoints];
        for(int i=0; i<numPoints; i++){
            x[i] = xList.get(i).getValue();
        }
        return x;
    }
    public double[] getY(){
        List<DataValue> yList = new ArrayList<>();
        for(DataValue d : dataValues){
            if(d.getIsX() == false){
                yList.add(d);
            }
        }
        double[] y = new double[numPoints];
        for(int i=0; i<numPoints; i++){
            y[i] = yList.get(i).getValue();
        }
        return y;
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
    public List<DataValue> getDataValues(){
        return dataValues;
    }
    public void setDataValues(List<DataValue> dataValues){
        this.dataValues = dataValues;
    }
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
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }

    /** Makes the Life Expectancy by Year sample dataset */
    public static Data makeLifeDataset(){
        Data data = new Data();
        List<DataValue> dataValues = new ArrayList<>();
        for(int i=1881; i<2019; i++){
            dataValues.add(new DataValue(i, data, true));
        }
        dataValues.add(new DataValue(40, data, false));
        dataValues.add(new DataValue(40.6, data, false));
        dataValues.add(new DataValue(41.2, data, false));
        dataValues.add(new DataValue(41.7, data, false));
        dataValues.add(new DataValue(42.3, data, false));
        dataValues.add(new DataValue(42.9, data, false));
        dataValues.add(new DataValue(43.5, data, false));
        dataValues.add(new DataValue(44.1, data, false));
        dataValues.add(new DataValue(44.6, data, false));
        dataValues.add(new DataValue(45.2, data, false));
        dataValues.add(new DataValue(45.6, data, false));
        dataValues.add(new DataValue(46, data, false));
        dataValues.add(new DataValue(46.3, data, false));
        dataValues.add(new DataValue(46.7, data, false));
        dataValues.add(new DataValue(47.1, data, false));
        dataValues.add(new DataValue(47.5, data, false));
        dataValues.add(new DataValue(47.8, data, false));
        dataValues.add(new DataValue(48.2, data, false));
        dataValues.add(new DataValue(49, data, false));
        dataValues.add(new DataValue(49.3, data, false));
        dataValues.add(new DataValue(50.5, data, false));
        dataValues.add(new DataValue(50.6, data, false));
        dataValues.add(new DataValue(49.6, data, false));
        dataValues.add(new DataValue(50.3, data, false));
        dataValues.add(new DataValue(50.1, data, false));
        dataValues.add(new DataValue(50.2, data, false));
        dataValues.add(new DataValue(51.9, data, false));
        dataValues.add(new DataValue(52.8, data, false));
        dataValues.add(new DataValue(51.8, data, false));
        dataValues.add(new DataValue(53.4, data, false));
        dataValues.add(new DataValue(54.1, data, false));
        dataValues.add(new DataValue(53.5, data, false));
        dataValues.add(new DataValue(54.6, data, false));
        dataValues.add(new DataValue(55.1, data, false));
        dataValues.add(new DataValue(54.2, data, false));
        dataValues.add(new DataValue(54, data, false));
        dataValues.add(new DataValue(47.2, data, false));
        dataValues.add(new DataValue(55.3, data, false));
        dataValues.add(new DataValue(55.4, data, false));
        dataValues.add(new DataValue(55.3, data, false));
        dataValues.add(new DataValue(58.3, data, false));
        dataValues.add(new DataValue(58.1, data, false));
        dataValues.add(new DataValue(57.5, data, false));
        dataValues.add(new DataValue(58.5, data, false));
        dataValues.add(new DataValue(58.5, data, false));
        dataValues.add(new DataValue(58, data, false));
        dataValues.add(new DataValue(59.5, data, false));
        dataValues.add(new DataValue(58.4, data, false));
        dataValues.add(new DataValue(58.5, data, false));
        dataValues.add(new DataValue(59.7, data, false));
        dataValues.add(new DataValue(60.4, data, false));
        dataValues.add(new DataValue(61.1, data, false));
        dataValues.add(new DataValue(61, data, false));
        dataValues.add(new DataValue(60.4, data, false));
        dataValues.add(new DataValue(61, data, false));
        dataValues.add(new DataValue(60.5, data, false));
        dataValues.add(new DataValue(61.2, data, false));
        dataValues.add(new DataValue(62.5, data, false));
        dataValues.add(new DataValue(63.2, data, false));
        dataValues.add(new DataValue(63.4, data, false));
        dataValues.add(new DataValue(63.9, data, false));
        dataValues.add(new DataValue(64.7, data, false));
        dataValues.add(new DataValue(64.4, data, false));
        dataValues.add(new DataValue(65.2, data, false));
        dataValues.add(new DataValue(65.7, data, false));
        dataValues.add(new DataValue(66.4, data, false));
        dataValues.add(new DataValue(66.8, data, false));
        dataValues.add(new DataValue(67.4, data, false));
        dataValues.add(new DataValue(67.8, data, false));
        dataValues.add(new DataValue(68.2, data, false));
        dataValues.add(new DataValue(68.3, data, false));
        dataValues.add(new DataValue(68.5, data, false));
        dataValues.add(new DataValue(68.9, data, false));
        dataValues.add(new DataValue(69.7, data, false));
        dataValues.add(new DataValue(69.7, data, false));
        dataValues.add(new DataValue(69.8, data, false));
        dataValues.add(new DataValue(69.6, data, false));
        dataValues.add(new DataValue(69.8, data, false));
        dataValues.add(new DataValue(70.1, data, false));
        dataValues.add(new DataValue(70, data, false));
        dataValues.add(new DataValue(70.4, data, false));
        dataValues.add(new DataValue(70.3, data, false));
        dataValues.add(new DataValue(70.1, data, false));
        dataValues.add(new DataValue(70.4, data, false));
        dataValues.add(new DataValue(70.5, data, false));
        dataValues.add(new DataValue(70.6, data, false));
        dataValues.add(new DataValue(70.9, data, false));
        dataValues.add(new DataValue(70.5, data, false));
        dataValues.add(new DataValue(70.8, data, false));
        dataValues.add(new DataValue(71, data, false));
        dataValues.add(new DataValue(71.3, data, false));
        dataValues.add(new DataValue(71.5, data, false));
        dataValues.add(new DataValue(71.8, data, false));
        dataValues.add(new DataValue(72.2, data, false));
        dataValues.add(new DataValue(72.6, data, false));
        dataValues.add(new DataValue(73, data, false));
        dataValues.add(new DataValue(73.3, data, false));
        dataValues.add(new DataValue(73.6, data, false));
        dataValues.add(new DataValue(73.9, data, false));
        dataValues.add(new DataValue(74.1, data, false));
        dataValues.add(new DataValue(74.4, data, false));
        dataValues.add(new DataValue(74.6, data, false));
        dataValues.add(new DataValue(74.7, data, false));
        dataValues.add(new DataValue(74.8, data, false));
        dataValues.add(new DataValue(74.9, data, false));
        dataValues.add(new DataValue(75, data, false));
        dataValues.add(new DataValue(75.1, data, false));
        dataValues.add(new DataValue(75.2, data, false));
        dataValues.add(new DataValue(75.3, data, false));
        dataValues.add(new DataValue(75.6, data, false));
        dataValues.add(new DataValue(75.7, data, false));
        dataValues.add(new DataValue(75.9, data, false));
        dataValues.add(new DataValue(75.8, data, false));
        dataValues.add(new DataValue(75.8, data, false));
        dataValues.add(new DataValue(75.9, data, false));
        dataValues.add(new DataValue(76.3, data, false));
        dataValues.add(new DataValue(76.6, data, false));
        dataValues.add(new DataValue(76.8, data, false));
        dataValues.add(new DataValue(76.9, data, false));
        dataValues.add(new DataValue(76.9, data, false));
        dataValues.add(new DataValue(77, data, false));
        dataValues.add(new DataValue(77.2, data, false));
        dataValues.add(new DataValue(77.5, data, false));
        dataValues.add(new DataValue(77.6, data, false));
        dataValues.add(new DataValue(77.8, data, false));
        dataValues.add(new DataValue(78, data, false));
        dataValues.add(new DataValue(78.2, data, false));
        dataValues.add(new DataValue(78.4, data, false));
        dataValues.add(new DataValue(78.7, data, false));
        dataValues.add(new DataValue(78.8, data, false));
        dataValues.add(new DataValue(78.9, data, false));
        dataValues.add(new DataValue(78.9, data, false));
        dataValues.add(new DataValue(78.9, data, false));
        dataValues.add(new DataValue(78.6, data, false));
        dataValues.add(new DataValue(78.6, data, false));
        dataValues.add(new DataValue(78.6, data, false));
        dataValues.add(new DataValue(78.6, data, false));

        data.setDataValues(dataValues);
        data.setNumPoints(dataValues.size() / 2);
        data.setName("Average Life Expectancy by Year");
        data.setXLabel("Year");
        data.setYLabel("Life Expectancy (Years)");
        data.setItemLabel("Average Life Expectancy per Year");

        return data;
    }

    /** Makes the Weight and Price of Chocolate Boxes sample dataset */
    public static Data makeChocolateDataset(){
        Data data = new Data();
        List<DataValue> dataValues = new ArrayList<>();
        dataValues.add(new DataValue(3, data, true));
        dataValues.add(new DataValue(1, data, false));
        dataValues.add(new DataValue(3.2, data, true));
        dataValues.add(new DataValue(1.4, data, false));
        dataValues.add(new DataValue(3.5, data, true));
        dataValues.add(new DataValue(.8, data, false));
        dataValues.add(new DataValue(3.7, data, true));
        dataValues.add(new DataValue(2.5, data, false));
        dataValues.add(new DataValue(4, data, true));
        dataValues.add(new DataValue(1.3, data, false));
        dataValues.add(new DataValue(4.15, data, true));
        dataValues.add(new DataValue(2, data, false));
        dataValues.add(new DataValue(4.30, data, true));
        dataValues.add(new DataValue(1.8, data, false));
        dataValues.add(new DataValue(4.60, data, true));
        dataValues.add(new DataValue(2, data, false));
        dataValues.add(new DataValue(4.6, data, true));
        dataValues.add(new DataValue(1.1, data, false));
        dataValues.add(new DataValue(5, data, true));
        dataValues.add(new DataValue(3, data, false));
        dataValues.add(new DataValue(5, data, true));
        dataValues.add(new DataValue(2.2, data, false));
        dataValues.add(new DataValue(5.10, data, true));
        dataValues.add(new DataValue(2.5, data, false));
        dataValues.add(new DataValue(5.45, data, true));
        dataValues.add(new DataValue(1.9, data, false));
        dataValues.add(new DataValue(5.70, data, true));
        dataValues.add(new DataValue(2.3, data, false));
        dataValues.add(new DataValue(5.85, data, true));
        dataValues.add(new DataValue(3, data, false));
        dataValues.add(new DataValue(6, data, true));
        dataValues.add(new DataValue(2.3, data, false));
        dataValues.add(new DataValue(6, data, true));
        dataValues.add(new DataValue(3.4, data, false));
        dataValues.add(new DataValue(6, data, true));
        dataValues.add(new DataValue(3.5, data, false));
        dataValues.add(new DataValue(6.40, data, true));
        dataValues.add(new DataValue(3.8, data, false));
        dataValues.add(new DataValue(6.5, data, true));
        dataValues.add(new DataValue(3.5, data, false));
        dataValues.add(new DataValue(6.60, data, true));
        dataValues.add(new DataValue(3.9, data, false));
        dataValues.add(new DataValue(6.60, data, true));
        dataValues.add(new DataValue(4.9, data, false));
        dataValues.add(new DataValue(6.80, data, true));
        dataValues.add(new DataValue(4, data, false));
        dataValues.add(new DataValue(7, data, true));
        dataValues.add(new DataValue(5, data, false));
        dataValues.add(new DataValue(7.10, data, true));
        dataValues.add(new DataValue(4.3, data, false));
        dataValues.add(new DataValue(7.2, data, true));
        dataValues.add(new DataValue(3.3, data, false));
        dataValues.add(new DataValue(7.25, data, true));
        dataValues.add(new DataValue(4.5, data, false));
        dataValues.add(new DataValue(7.50, data, true));
        dataValues.add(new DataValue(4.2, data, false));
        dataValues.add(new DataValue(7.60, data, true));
        dataValues.add(new DataValue(5, data, false));
        dataValues.add(new DataValue(8, data, true));
        dataValues.add(new DataValue(4.8, data, false));

        data.setDataValues(dataValues);
        data.setNumPoints(dataValues.size() / 2);
        data.setName("Weight and Price of Chocolate Boxes");
        data.setXLabel("Price of Chocolate Box (Dollars)");
        data.setYLabel("Weight of Chocolate Box (Ounces)");
        data.setItemLabel("Chocolate Box");

        return data;
    } 

        /** Makes the Price and Number of Pages of Books sample dataset */
        public static Data makeBookDataset(){
            Data data = new Data();
            List<DataValue> dataValues = new ArrayList<>();
            dataValues.add(new DataValue(5, data, true));
            dataValues.add(new DataValue(60, data, false));
            dataValues.add(new DataValue(5, data, true));
            dataValues.add(new DataValue(20, data, false));
            dataValues.add(new DataValue(5.30, data, true));
            dataValues.add(new DataValue(100, data, false));
            dataValues.add(new DataValue(7, data, true));
            dataValues.add(new DataValue(80, data, false));
            dataValues.add(new DataValue(7.30, data, true));
            dataValues.add(new DataValue(180, data, false));
            dataValues.add(new DataValue(9, data, true));
            dataValues.add(new DataValue(432, data, false));
            dataValues.add(new DataValue(10, data, true));
            dataValues.add(new DataValue(35, data, false));
            dataValues.add(new DataValue(10.25, data, true));
            dataValues.add(new DataValue(100, data, false));
            dataValues.add(new DataValue(12, data, true));
            dataValues.add(new DataValue(200, data, false));
            dataValues.add(new DataValue(12.50, data, true));
            dataValues.add(new DataValue(15, data, false));
            dataValues.add(new DataValue(15.75, data, true));
            dataValues.add(new DataValue(300, data, false));
            dataValues.add(new DataValue(18, data, true));
            dataValues.add(new DataValue(320, data, false));
            dataValues.add(new DataValue(18, data, true));
            dataValues.add(new DataValue(148, data, false));
            dataValues.add(new DataValue(20, data, true));
            dataValues.add(new DataValue(498, data, false));
            dataValues.add(new DataValue(20.25, data, true));
            dataValues.add(new DataValue(152, data, false));
            dataValues.add(new DataValue(20.50, data, true));
            dataValues.add(new DataValue(68, data, false));
            dataValues.add(new DataValue(23, data, true));
            dataValues.add(new DataValue(325, data, false));
            dataValues.add(new DataValue(25, data, true));
            dataValues.add(new DataValue(112, data, false));
            dataValues.add(new DataValue(26, data, true));
            dataValues.add(new DataValue(264, data, false));
            dataValues.add(new DataValue(27, data, true));
            dataValues.add(new DataValue(411, data, false));
            dataValues.add(new DataValue(27.75, data, true));
            dataValues.add(new DataValue(74, data, false));
            dataValues.add(new DataValue(30, data, true));
            dataValues.add(new DataValue(107, data, false));

            data.setDataValues(dataValues);
            data.setNumPoints(dataValues.size() / 2);
            data.setName("Price and Number of Pages of Books");
            data.setXLabel("Price of Book (Dollars)");
            data.setYLabel("Number of Pages in Book");
            data.setItemLabel("Book");
    
            return data;
        } 
}
