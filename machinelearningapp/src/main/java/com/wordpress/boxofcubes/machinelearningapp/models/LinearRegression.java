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

        // Each set will have x and y values
        /*double[][] trainingVals = new double[2][trainingSize];
        double[][] crossVals = new double[2][crossSize];
        double[][] testingVals = new double[2][testingSize];*/
        List<DataValue> trainingVals = new ArrayList<>();
        List<DataValue> crossVals = new ArrayList<>();
        List<DataValue> testingVals = new ArrayList<>();
        Random random = new Random();
        List<Integer> alreadyUsed = new ArrayList<>();

        // Loop through each index, pick a random pair of data points, and assign that 
        // pair as the next member of the relevant subset
        for(int i=0; i < allData.getNumPoints(); i++){
            System.out.println("This iteration: "+i);
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
                //trainingVals[0][i] = allData.getX()[dataInd];
                //trainingVals[1][i] = allData.getY()[dataInd];
                trainingVals.add(new DataValue(allData.getX()[dataInd], allData, true));
                trainingVals.add(new DataValue(allData.getY()[dataInd], allData, false));

            }else if(i < trainingSize + crossSize){
                int ind = i - trainingSize;
                //crossVals[0][ind] = allData.getX()[dataInd];
                //crossVals[1][ind] = allData.getY()[dataInd];
                crossVals.add(new DataValue(allData.getX()[dataInd], allData, true));
                crossVals.add(new DataValue(allData.getY()[dataInd], allData, false));
            }else{
                int ind = i - trainingSize - crossSize;
                //testingVals[0][ind] = allData.getX()[dataInd];
                //testingVals[1][ind] = allData.getY()[dataInd];
                testingVals.add(new DataValue(allData.getX()[dataInd], allData, true));
                testingVals.add(new DataValue(allData.getY()[dataInd], allData, false));
            }
        }

        // Set the subsets to the new lists of values
        trainingSet = new Data(trainingVals);
        crossValSet = new Data(crossVals);
        testingSet = new Data(testingVals);
    }



    public static void main(String[] args){
        Data data = Data.makeChocolateDataset();
        ParametersDTO p = ParametersDTO.getDefaultParameters();
        double[] theta = {p.getTheta0(), p.getTheta1()};
        Parameters parameters = new Parameters(p.getTrainingProportion(), theta, p.getAlpha(), p.getLambda(), p.getMaxIterations(), p.getConvergenceLevel());

        

    }




}
