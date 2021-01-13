package com.wordpress.boxofcubes.machinelearningapp.validation;

import com.wordpress.boxofcubes.machinelearningapp.models.dto.DataSubmissionDTO;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class DataSubmissionDTOValidator implements Validator{
    @Override
    public boolean supports(Class clazz){
        return DataSubmissionDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors){
        DataSubmissionDTO d = (DataSubmissionDTO)object;
        File newX; 
        File newY;

        // Working with file uploads
        if(!d.getXFile().isEmpty() || !d.getYFile().isEmpty()){
            errors.reject("error.missingBoth", "AT LEAST ONE UPLOAD");
            // Y file is missing
            if(!d.getXFile().isEmpty() && d.getYFile().isEmpty()){
                errors.reject("error.missingYFile", "Y data file is missing");
            }
            // X file is missing
            else if(d.getXFile().isEmpty() && !d.getYFile().isEmpty()){
                errors.reject("error.missingXFile", "X data file is missing");
            }
            // Both files are present
            else{
            // Convert X file to File and scan
                try{
                    newX = new File(d.getXFile().getOriginalFilename());
                    d.getXFile().transferTo(newX);
                    scanFile(newX, d, "X");
                }catch(FileNotFoundException e){
                    errors.reject("error.xFileNotFound", "X data file can't be found");
                }catch(InputMismatchException e){
                    errors.reject("error.xFileHasNonNumber", "X data file contains non-numbers");
                }catch(IOException e){
                    errors.reject("error.xFileFailed", "Error uploading X data file");
                }

            // Convert Y file to File
                try{
                    newY = new File(d.getYFile().getOriginalFilename());
                    d.getYFile().transferTo(newY);
                    scanFile(newY, d, "Y");
                }catch(FileNotFoundException e){
                    errors.reject("error.yFileNotFound", "Y data file can't be found");
                }catch(IOException e){
                    errors.reject("error.yFileFailed", "Error uploading Y data file");
                }
            }
        }

        // and then for entries

        // Check if the file contains only doubles
        /*ArrayList<Double> xVals = new ArrayList<>();
        ArrayList<Double> yVals = new ArrayList<>();
        Scanner scan = null;
        try{
            scan = new Scanner(newX);
            while(scan.hasNext()){
                xVals.add(scan.nextDouble());
            }
        }catch(FileNotFoundException e){
            errors.reject("error.xFileNotFoundWhileScanning", "X data file can't be found to read");
        }catch(InputMismatchException e){
            errors.reject("error.xFileHasNonnumbers", "X data file contains non-numbers");
        }*/





            
        /*if((d.getXFile().isEmpty() && d.getYFile().isEmpty()) || 
        (d.getXEntry().isEmpty() && d.getYEntry().isEmpty())){
            errors.reject("error.missingBoth", "X and Y data are missing");
        }
        // Y missing
        else if((!d.getXFile().isEmpty() && d.getYFile().isEmpty()) || 
        (!d.getXEntry().isEmpty() && d.getYEntry().isEmpty())){
            errors.reject("error.missingY", "Y data is missing");
        }
        // X missing
        else if((d.getXFile().isEmpty() && !d.getYFile().isEmpty()) || 
        (d.getXEntry().isEmpty() && !d.getYEntry().isEmpty())){
            errors.reject("error.missingX", "X data is missing");
        }*/

        // Name, xLabel, yLabel, itemLabel missing
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "Name of dataset is missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "xLabel", "error.xLabel", "Label for X data is missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yLabel", "error.yLabel", "Label for Y data is missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemLabel", "error.itemLabel", "Label for item per example is missing");

        /*File newX;
        File newY;
        if(!d.getXFile().isEmpty() && !d.getYFile().isEmpty()){
            try{
                newX = new File(d.getXFile().getOriginalFilename());
                d.getXFile().transferTo(newX);
            }catch(FileNotFoundException e){
                errors.reject("error.xFileNotFound", "X data file can't be found");
            }catch(IOException e){
                errors.reject("error.xFileFailed", "Error uploading X data file");
            }
        }*/


    }

    private void scanFile(File file, DataSubmissionDTO submission, String xOrY) throws FileNotFoundException, InputMismatchException{
        ArrayList<Double> vals = new ArrayList<>();
        Scanner scan = new Scanner(file);
        while(scan.hasNext()){
            vals.add(scan.nextDouble());
        }
        scan.close();

        double[] theVals = new double[vals.size()];
        for(int i=0; i<vals.size(); i++){
            theVals[i] = vals.get(i);
        }

        if(xOrY.equals("X")){
            submission.setX(theVals);
            System.out.println("NEW X FOR OBJECT: "+submission.getX());
        }else if(xOrY.equals("Y")){
            submission.setY(theVals);
            System.out.println("NEW Y FOR OBJECT: "+submission.getY());
        }
    }


}
