public class MachineLearning{
    /** Returns a matrix where the first column consists in 1s and the second is x values */
    public static double[][] getDesignMatrix(Data data){
        double[][] designMatrix = new double[data.numPoints][2];
        for(int row=0; row<data.numPoints; row++){
            designMatrix[row][0] = 1;
            designMatrix[row][1] = data.x[row];
        }
        return designMatrix;
    }

    /** Returns the model's (theta's) predicted y-values given the x-values */
    public static double[] getPredictions(Data data, double[] theta){
        double[] result = new double[data.numPoints];
        for(int row=0; row<data.numPoints; row++){
            result[row] = theta[0] + (data.x[row] * theta[1]);
        }
        return result;
    }

    /** Returns the squares of the models' predictions minus the actual y-values */
    public static double[] getSquaredError(Data data, double[] theta){
        double[] squaredError = new double[data.numPoints];
        double[] predictions = getPredictions(data, theta);
        for(int i=0; i<data.numPoints; i++){
            squaredError[i] = (predictions[i] - data.y[i]) * (predictions[i] - data.y[i]);
        }
        return squaredError;
    }

    /** Adds the squared errors of the model's predictions */
    public static double getSumOfSquares(Data data, double[] theta){
        double[] sqrError = getSquaredError(data, theta);
        double sumSquares = 0;
        
        for(int i=0; i<sqrError.length; i++){
            sumSquares += sqrError[i];
        }
        return sumSquares;
    }

    /** Returns the cost of the model */
    public static double getCost(Data data, double[] theta){
        double sumSquares = getSumOfSquares(data, theta);
        double cost = 2 * data.numPoints;
        cost = 1/cost;
        cost = cost * sumSquares;
        return cost;
    }

    /** Returns the updated theta vector for a single iteration of gradient descent  */
    public static double[] updateTheta(Data data, double[] theta, double alpha){
        double[] newTheta = new double[2];

        double[] predictions = getPredictions(data, theta);
        //System.out.println("PREDICTIONS LENGTH: "+predictions.length);
        //System.out.println("NUMPOINTS: "+data.numPoints);

        double[] subY = new double[predictions.length];
        for(int i=0; i<predictions.length; i++){
            subY[i] = predictions[i] - data.y[i];
            //System.out.println("sub y"+subY[i]);
        }

        double[][] design = getDesignMatrix(data);

        double[] timesX = new double[2];
        for(int a=0; a<2; a++){
            double thisSum = 0;
            for(int i=0; i<data.numPoints; i++){
                // i goes through each x value
                thisSum += design[i][a] * subY[i];
            }
            timesX[a] = thisSum;
            //System.out.println("A SUM: "+thisSum);
        }

        timesX[0] = timesX[0] * alpha/data.numPoints;
        timesX[1] = timesX[1] * alpha/data.numPoints;

        newTheta[0] = theta[0] - timesX[0];
        newTheta[1] = theta[1] - timesX[1];

        //System.out.println("NEW THETA 0: "+newTheta[0]);
        //System.out.println("NEW THETA 1: "+newTheta[1]);

        return newTheta;
    }

    /** Runs gradient descent and returns a matrix wherein the first row is the cost per iteration and the second row is the final theta values
     */
    public static double[][] gradientDescent(Data data, double[] theta,
    double alpha, int iterations){
        double[][] costAndTheta = new double[2][iterations];

        for(int i=0; i<iterations; i++){
            theta = updateTheta(data, theta, alpha);
            double cost = getCost(data, theta);

            costAndTheta[0][i] = cost;
            costAndTheta[1][0] = theta[0];
            costAndTheta[1][1] = theta[1];
        }
        return costAndTheta;
    }



    public static void main(String[] args){
        Data data = new Data("chocolateprices.txt", "numberofchocolates.txt");

        
        GraphBad scatter = new GraphBad("Chocolates Graph", data,
                                "Price of Box and Number of Chocolates per Box",
                                "Price", "Number of Chocolates",
                                "Box of Chocolates");
        scatter.displayGraph(true);

        //System.out.println(multiplyVectors(data));
        /*
        float[][] design = getDesignMatrix(data);
        for(int i=0; i<data.numPoints; i++){
            for(int a=0; a<2; a++){
                System.out.print(design[i][a]+" ");
            }
            System.out.println("");
        }*/

        double[] theta = {2,2};
        System.out.println("INITIAL COST: "+getCost(data, theta));
        double[] predictions = getPredictions(data, theta);
        for(int i=0; i<predictions.length; i++){
            System.out.print(predictions[i]+" ");
        }
        System.out.println("");
        double[] sqrError = getSquaredError(data, theta);
        for(int i=0; i<sqrError.length; i++){
            System.out.print(sqrError[i]+" ");
        }

        System.out.println("");
        System.out.println("Sum of squares: "+getSumOfSquares(data, theta));
        //System.out.println("UPDATE THETA: "+updateTheta(data, theta, (float).01));
        double[][] updated = gradientDescent(data, theta, .01, 500);
        for(int i=0; i<500; i++){
            System.out.println(updated[0][i]);
        }
        System.out.println("New theta 0: "+updated[1][0]);
        System.out.println("New theta 1: "+updated[1][1]);
        System.out.println("New cost: "+updated[0][499]);

        Model model = new Model(data, theta);

        System.out.println("First cost: "+model.cost);
        double[][] costAndTheta = model.improveModel(.01, 500);
        System.out.println("Updated cost: "+model.cost);

        GraphBad scatterWithModel = new GraphBad("Chocolates Graph", data, model,
        "Price of Box and Number of Chocolates per Box AND PREDICTIONS",
        "Price", "Number of Chocolates",
        "Box of Chocolates");
        scatterWithModel.displayGraph(true);


        double[] justCosts = costAndTheta[0];
        GraphBad costValues = new GraphBad("Gradient Descent", justCosts,
                            "Gradient Descent", "Number of Iterations", "Cost", 
                            "Iteration of Gradient Descent");
        costValues.displayGraph(true);

    }
}
