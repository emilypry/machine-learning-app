package com.wordpress.boxofcubes.machinelearningapp.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.wordpress.boxofcubes.machinelearningapp.models.dto.ParametersDTO;

public class LinearRegression {
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
    boolean costIncreased;
    boolean converged;

    // The model and errors
    private double[] trainedTheta;
    private double trainingError;
    private double crossValError;
    private double testingError;

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
    private double[][] getDesignMatrix(Data dataset){
        double[][] designMatrix = new double[dataset.getNumPoints()][2];
        for(int row=0; row < dataset.getNumPoints(); row++){
            designMatrix[row][0] = 1;
            designMatrix[row][1] = dataset.getX()[row];
        }
        return designMatrix;
    }

    /** Returns the model's (theta's) predicted y-values given the x-values in the dataset */
    public double[] getPredictions(Data dataset, double[] theta){
        double[] predictions = new double[dataset.getNumPoints()];
        for(int row=0; row < dataset.getNumPoints(); row++){
            predictions[row] = theta[0] + (dataset.getX()[row] * theta[1]);
        }
        return predictions;
    }

    /** Returns the cost of the model, with or without regularization (with, if lambda
     * is greater than 0 and regularized == true) */
    public double getCost(Data dataset, double[] theta, boolean regularized){
        // cost = 1/(2*m) * sum(sqr(predictions - y))
        double sumOfSquares = 0;
        double[] predictions = getPredictions(dataset, theta);

        // sum(sqr(predictions - y))
        for(int i=0; i<dataset.getNumPoints(); i++){
            sumOfSquares += (predictions[i] - dataset.getY()[i]) 
                            * (predictions[i] - dataset.getY()[i]);
        }

        // 1/(2*m) * sum(sqr(predictions - y))
        double cost = 2 * dataset.getNumPoints();
        cost = 1/cost;
        cost *= sumOfSquares;

        // If doing a regularized cost function, add the regularization parameter
        if(regularized == true && parameters.getLambda() > 0){
            // Add lambda/(2*m) * sum(theta[1]*theta[1])
            double reg = 2 * dataset.getNumPoints();
            reg = parameters.getLambda() / reg;
            reg *= (theta[1]*theta[1]);
            cost += reg;
        }
        return cost;
    }

    /** Returns the updated theta vector for a single iteration of gradient descent  */
    private double[] updateTheta(Data dataset, double[] theta){
        // If not regularized (lambda = 0):
        //   newTheta = theta - (alpha/m) * ((predictions - y) * X)
        // If regularized (lambda > 0):
        //   newTheta[0] = theta[0] - (alpha/m) * ((predictions - y) * X[i][0])
        //   newTheta[1] = theta[1] - (alpha/m) * ((predictions - y) * X[i][1]) + 
        //                  (lambda / m) * theta[1]

        double[] newTheta = new double[2];
        double[] predictions = getPredictions(dataset, theta);
        double[][] design = getDesignMatrix(dataset);

        // Get (predictions - y) for all versions
        double[] predMinusY = new double[predictions.length];
        for(int i=0; i<predictions.length; i++){
            predMinusY[i] = predictions[i] - dataset.getY()[i];
        }

        // Get (alpha/m) for all versions
        double alphaOverM = parameters.getAlpha() / dataset.getNumPoints();

        double[] thetaUpdates = new double[2];

        // If regularized
        if(parameters.getLambda() > 0){
            // For newTheta[0], predMinusY * X[0] is the sum of all predMinusY values
            for(double d : predMinusY){
                thetaUpdates[0] += d;
            }

            // For newTheta[1], predMinusY * X[1] is the sum of all (predMinusY[i] * X[i][1])
            double thisSum = 0;
            for(int i=0; i<dataset.getNumPoints(); i++){
                thisSum += predMinusY[i] * design[i][1];
            }
            thetaUpdates[1] = thisSum;

            // Get alphaOverM * (predMinusY * X[i]) [+ (lambda/m)*theta[1] for newTheta[1]]
            thetaUpdates[0] *= alphaOverM;
            thetaUpdates[1] *= alphaOverM;

            // For newTheta[1], add (lambda / m) * theta[1]
            thetaUpdates[1] += (parameters.getLambda() /dataset.getNumPoints() * theta[1]);

        // If no regularization
        }else{
            // Get predMinusY * X
            for(int col=0; col<2; col++){
                double thisSum = 0;
                for(int row=0; row<dataset.getNumPoints(); row++){
                    thisSum += design[row][col] * predMinusY[row];
                }
                thetaUpdates[col] = thisSum;
            }

            // Get alphaOverM * (predMinusY * X)
            thetaUpdates[0] *= alphaOverM;
            thetaUpdates[1] *= alphaOverM;           
        }

        // Get theta - thetaUpdates
        newTheta[0] = theta[0] - thetaUpdates[0];
        newTheta[1] = theta[1] - thetaUpdates[1];

        return newTheta;
    }

    /** Runs gradient descent and returns a matrix where the first row is the cost per
     * iteration and the second row is the final two theta values
     */
    private double[][] gradientDescent(Data dataset){
        ArrayList<Double> costsList = new ArrayList<Double>();
        double[] theta = parameters.getInitialTheta();

        for(int i=0; i<parameters.getMaxIterations(); i++){
            theta = updateTheta(dataset, theta);
            double cost = getCost(dataset, theta, true);
            costsList.add(cost);

            System.out.print(cost + " ");

            if(i > 0){
                double difference = costsList.get(i-1) - cost;

                // If cost has increased on this iteration, stop (not learning)
                if(difference < 0){
                    System.out.println("COST INCREASED");
                    costIncreased = true;
                    break;
                }
                // If the cost has decreased less than the convergenceLevel on this 
                // iteration, stop (learned)
                if(difference < parameters.getConvergenceLevel()){
                    System.out.println("CONVERGED");
                    converged = true;
                    break;
                }
            }
        }

        double[][] costsAndTheta = new double[2][costsList.size()];
        for(int i=0; i<costsList.size(); i++){
            costsAndTheta[0][i] = costsList.get(i);
        }
        costsAndTheta[1] = theta;

        return costsAndTheta;
    }

    /** Runs gradient descent on the trainingSet and updates attributes */
    public void trainModel(){
        double[][] costsAndTheta = gradientDescent(trainingSet);
        actualIterations = costsAndTheta[0].length;
        costsWhileTraining = costsAndTheta[0];
        trainedTheta = costsAndTheta[1];
        trainingError = getCost(trainingSet, trainedTheta, false);
        crossValError = getCost(crossValSet, trainedTheta, false);
        testingError = getCost(testingSet, trainedTheta, false);
    }

    public int getTrainingSize(){
        return trainingSet.getNumPoints();
    }
    public int getCrossValSize(){
        return crossValSet.getNumPoints();
    }
    public int getTestingSize(){
        return testingSet.getNumPoints();
    }
    public double getInitialCost(){
        return getCost(trainingSet, parameters.getInitialTheta(), false);
    }


    public int getId(){
        return id;
    }
    public Data getAllData(){
        return allData;
    }
    public void setAllData(Data allData){
        this.allData = allData;
    }
    public Parameters getParameters(){
        return parameters;
    }
    public void setParameters(Parameters parameters){
        this.parameters = parameters;
    }
    public int getActualIterations(){
        return actualIterations;
    }
    public double[] getCostsWhileTraining(){
        return costsWhileTraining;
    }
    public boolean getCostIncreased(){
        return costIncreased;
    }
    public boolean getConverged(){
        return converged;
    }
    public double[] getTrainedTheta(){
        return trainedTheta;
    }
    public double getTrainingError(){
        return trainingError;
    }
    public double getCrossValError(){
        return crossValError;
    }
    public double getTestingError(){
        return testingError;
    }






    public static void main(String[] args){
        Data data = Data.makeLifeDataset();
        ParametersDTO p = ParametersDTO.getDefaultParameters();
        double[] theta = {p.getTheta0(), p.getTheta1()};
        Parameters parameters = new Parameters(p.getTrainingProportion(), theta, p.getAlpha(), p.getLambda(), p.getMaxIterations(), p.getConvergenceLevel());

        LinearRegression lr = new LinearRegression(data, parameters);

        /*System.out.println("ORIGINAL");
        for(int i=0; i< lr.allData.getNumPoints(); i++){
            System.out.println(lr.allData.getX()[i]+", "+lr.allData.getY()[i]);
        }*/
        System.out.println("TRAINING");
        for(int i=0; i< lr.trainingSet.getNumPoints(); i++){
            System.out.println(lr.trainingSet.getX()[i]+", "+lr.trainingSet.getY()[i]);
        }
        /*System.out.println("CV");
        for(int i=0; i< lr.crossValSet.getNumPoints(); i++){
            System.out.println(lr.crossValSet.getX()[i]+", "+lr.crossValSet.getY()[i]);
        }
        System.out.println("TESTING");
        for(int i=0; i< lr.testingSet.getNumPoints(); i++){
            System.out.println(lr.testingSet.getX()[i]+", "+lr.testingSet.getY()[i]);
        }*/

        /*System.out.println("design");
        double[][] design = getDesignMatrix(lr.trainingSet);
        for(int r=0; r<design.length; r++){
            System.out.println(design[r][0]+" "+design[r][1]);
        }*/
        /*System.out.println("predictions");
        double[] pred = getPredictions(lr.trainingSet, new double[]{1,1});
        for(double pp : pred){
            System.out.println(pp);
        }*/

        /*System.out.println("theta: "+lr.parameters.getInitialTheta()[0]+" "+lr.parameters.getInitialTheta()[1]);
        lr.parameters.setLambda(20);
        System.out.println("lambda: "+lr.parameters.getLambda());
        double cost = lr.getCost(lr.trainingSet, lr.parameters.getInitialTheta(), true);
        System.out.println("COST: "+cost);*/

        /*lr.parameters.setLambda(20);
        double[] newTheta = lr.updateTheta(lr.trainingSet, theta);
        System.out.println(newTheta[0]+" "+newTheta[1]);*/

        //double[] t = {10, 10};
        //lr.parameters.setInitialTheta(t);
        /*lr.parameters.setLambda(10);
        System.out.println("initial theta: "+lr.parameters.getInitialTheta()[0]+" "+lr.parameters.getInitialTheta()[1]);
        System.out.println("alpha: "+lr.parameters.getAlpha());
        System.out.println("lambda: "+lr.parameters.getLambda());
        System.out.println("max it: "+lr.parameters.getMaxIterations());
        System.out.println("con level: "+lr.parameters.getConvergenceLevel());
        double[][] costsAndTheta = lr.gradientDescent(lr.trainingSet);
        System.out.println("new theta: "+costsAndTheta[1][0]+" "+costsAndTheta[1][1]);*/

        lr.parameters.setAlpha(.00000001);
        lr.trainModel();
        System.out.println("trained model!");
        System.out.println("initial error: "+lr.getCost(lr.trainingSet, lr.parameters.getInitialTheta(), false));
        System.out.println("trained theta: "+lr.trainedTheta[0]+" "+lr.trainedTheta[1]);
        System.out.println("training error: "+lr.trainingError);
        System.out.println("cv error: "+lr.crossValError);
        System.out.println("testing error: "+lr.trainingError);




    }




}
