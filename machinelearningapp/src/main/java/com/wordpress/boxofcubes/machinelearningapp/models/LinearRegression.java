package com.wordpress.boxofcubes.machinelearningapp.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.wordpress.boxofcubes.machinelearningapp.models.dto.ParametersDTO;

@Entity
public class LinearRegression {
    @Id
    @GeneratedValue
    private int id;

    // The data and subsets
    private Data allData;
    private Data trainingSet;
    private Data crossValSet;
    private Data testingSet;

    // The parameters for the gradient descent algorithm
    private Parameters parameters;
    
    // Acquired during/after gradient descent
    private int actualIterations;
    private double[] costsWhileTraining;

    // The model
    private double[] trainedTheta;
    // will want to get t and cv cost too

    public LinearRegression(){}
    public LinearRegression(Data allData, Parameters parameters){
        this.allData = allData;
        this.parameters = parameters;
        setSubsets();
    }

    /** Sets the trainingSet, crossValSet, and testingSet from allData */
    private void setSubsets(){
        // Get the size for the subsets
        int trainingSize = (int)(allData.getNumPoints() * parameters.getTrainingProportion());
        int crossSize = (int)((allData.getNumPoints() - trainingSize) / 2);
        int testingSize = allData.getNumPoints() - trainingSize - crossSize;

        System.out.println("original size: "+allData.getNumPoints());
        System.out.println("training size: "+trainingSize);
        System.out.println("cross size: "+crossSize);
        System.out.println("testing size: "+testingSize);

        List<DataValue> trainingVals = new ArrayList<>();
        List<DataValue> crossVals = new ArrayList<>();
        List<DataValue> testingVals = new ArrayList<>();
        Random random = new Random();
        List<Integer> alreadyUsed = new ArrayList<>();

        // Loop through each index, pick a random pair of data points, and assign that 
        // pair as the next member of the relevant subset
        for(int i=0; i < allData.getNumPoints(); i++){
            // Get a random number
            int dataInd;
            while(true){
                dataInd = random.nextInt(allData.getNumPoints());
                // Make sure that dataInd hasn't already been used
                if(alreadyUsed.contains(dataInd) == false){
                    alreadyUsed.add(dataInd);
                    break;
                }
            }

            // Add the data points at dataInd to the relevant subset
            if(i < trainingSize){
                trainingVals.add(new DataValue(allData.getX()[dataInd], allData, true));
                trainingVals.add(new DataValue(allData.getY()[dataInd], allData, false));
            }else if(i < trainingSize + crossSize){
                crossVals.add(new DataValue(allData.getX()[dataInd], allData, true));
                crossVals.add(new DataValue(allData.getY()[dataInd], allData, false));
            }else{
                testingVals.add(new DataValue(allData.getX()[dataInd], allData, true));
                testingVals.add(new DataValue(allData.getY()[dataInd], allData, false));
            }
        }

        // Set the subsets to the new lists of values
        trainingSet = new Data(trainingVals);
        crossValSet = new Data(crossVals);
        testingSet = new Data(testingVals);
    }

    /** Returns a matrix where the first column consists in 1s and the second is x values */
    private static double[][] getDesignMatrix(Data dataset){
        double[][] designMatrix = new double[dataset.getNumPoints()][2];
        for(int row=0; row < dataset.getNumPoints(); row++){
            designMatrix[row][0] = 1;
            designMatrix[row][1] = dataset.getX()[row];
        }
        return designMatrix;
    }



    public static void main(String[] args){
        Data data = Data.makeBookDataset();
        ParametersDTO p = ParametersDTO.getDefaultParameters();
        double[] theta = {p.getTheta0(), p.getTheta1()};
        Parameters parameters = new Parameters(p.getTrainingProportion(), theta, p.getAlpha(), p.getLambda(), p.getMaxIterations(), p.getConvergenceLevel());

        LinearRegression lr = new LinearRegression(data, parameters);

        System.out.println("ORIGINAL");
        for(int i=0; i< lr.allData.getNumPoints(); i++){
            System.out.println(lr.allData.getX()[i]+", "+lr.allData.getY()[i]);
        }
        System.out.println("TRAINING");
        for(int i=0; i< lr.trainingSet.getNumPoints(); i++){
            System.out.println(lr.trainingSet.getX()[i]+", "+lr.trainingSet.getY()[i]);
        }
        System.out.println("CV");
        for(int i=0; i< lr.crossValSet.getNumPoints(); i++){
            System.out.println(lr.crossValSet.getX()[i]+", "+lr.crossValSet.getY()[i]);
        }
        System.out.println("TESTING");
        for(int i=0; i< lr.testingSet.getNumPoints(); i++){
            System.out.println(lr.testingSet.getX()[i]+", "+lr.testingSet.getY()[i]);
        }

        System.out.println("design");
        double[][] design = getDesignMatrix(lr.trainingSet);
        for(int r=0; r<design.length; r++){
            System.out.println(design[r][0]+" "+design[r][1]);
        }
        



    }




}
