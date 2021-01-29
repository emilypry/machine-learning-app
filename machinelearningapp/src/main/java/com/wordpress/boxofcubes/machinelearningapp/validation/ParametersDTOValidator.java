package com.wordpress.boxofcubes.machinelearningapp.validation;

import com.wordpress.boxofcubes.machinelearningapp.models.dto.ParametersDTO;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ParametersDTOValidator implements Validator{
    @Override
    public boolean supports(Class clazz){
        return ParametersDTOValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors){  
        ParametersDTO p = (ParametersDTO)object;
        
        // Parameters out of bounds
        if(p.getTrainingProportion() < .1 || p.getTrainingProportion() > .9){
            errors.reject("error.trainingProportion", "Training proportion must be between .1 and .9");
        }
        if(p.getAlpha() < 0 || p.getAlpha() > 1){
            errors.reject("error.alpha", "Alpha must be between 0 and 1");
        }
        if(p.getLambda() < 0 || p.getLambda() > 1000){
            errors.reject("error.lambda", "Lambda must be between 0 and 1000");
        }
        if(p.getMaxIterations() < 10 || p.getMaxIterations() > 3000){
            errors.reject("error.iterations", "Maximum iterations must be between 10 and 3000");
        }
        if(p.getConvergenceLevel() < .00000001 || p.getConvergenceLevel() > 1){
            errors.reject("error.convergence", "Convergence level must be between .00000001 and 1");
        }

        // Training proportion too high/low given number of points in data
        //System.out.println("Params - number of data points: "+p.getNumPointsInData());
        int train = (int)(p.getNumPointsInData() * p.getTrainingProportion());
        int cross = (int)((p.getNumPointsInData() - train) / 2);
        int test = p.getNumPointsInData() - train - cross;

        /*System.out.println("train size: " +train);
        System.out.println("cv size: " +cross);
        System.out.println("test size: " +test);*/

        if(train < 1){
            errors.reject("error.tooFewTrainingExamples", "Training proportion too low for dataset size");
        }
        if(cross < 1 || test < 1){
            errors.reject("error.tooFewCrossOrTestExamples", "Training proportion too high for dataset size");
        }

    }
}