package com.wordpress.boxofcubes.machinelearningapp.validation;

import com.wordpress.boxofcubes.machinelearningapp.models.dto.DataSubmissionDTO;

import org.apache.commons.io.FileUtils;
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
        File newX = new File("xSubmission");
        File newY = new File("ySubmission");
        Integer lengthX = null;
        Integer lengthY = null;

        // Working with file uploads (not null because appear in form)
        if(d.getXFile() != null || d.getYFile() != null){
            // Both entries are empty
            if(d.getXFile().isEmpty() && d.getYFile().isEmpty()){
                errors.reject("error.missingBothFiles", "X and Y files are missing");
            }
            // Y file is missing
            else if(!d.getXFile().isEmpty() && d.getYFile().isEmpty()){
                errors.reject("error.missingYFile", "Y data file is missing");
            }
            // X file is missing
            else if(d.getXFile().isEmpty() && !d.getYFile().isEmpty()){
                errors.reject("error.missingXFile", "X data file is missing");
            }
            // Both files are present
            else{
                // Convert X file to File, scan, and get new length of d.x
                try{
                    d.getXFile().transferTo(newX);
                    scanFile(newX, d, "X");
                    lengthX = d.getRawX().length;
                }catch(FileNotFoundException e){
                    errors.reject("error.xFileNotFound", "X data file can't be found");
                }catch(InputMismatchException e){
                    errors.reject("error.xFileHasNonNumber", "X data file contains non-numbers");
                }catch(IOException e){
                    errors.reject("error.xFileFailed", "Error uploading X data file");
                }

                // Convert Y file to File, scan, and get new length of d.y
                try{
                    d.getYFile().transferTo(newY);
                    scanFile(newY, d, "Y");
                    lengthY = d.getRawY().length;
                }catch(FileNotFoundException e){
                    errors.reject("error.yFileNotFound", "Y data file can't be found");
                }catch(InputMismatchException e){
                    errors.reject("error.yFileHasNonNumber", "Y data file contains non-numbers");
                }catch(IOException e){
                    errors.reject("error.yFileFailed", "Error uploading Y data file");
                }
            }
        }

        // Working with data entries (not null because appear in form)
        else if(d.getXEntry() != null || d.getYEntry() != null){
            // Both entries are empty
            if(d.getXEntry().isEmpty() && d.getYEntry().isEmpty()){
                errors.reject("error.missingBothEntries", "X and Y entries are missing");
            }
            // Y entry is missing
            else if(!d.getXEntry().isEmpty() && d.getYEntry().isEmpty()){
                errors.reject("error.missingYEntry", "Y data entry is missing");
            }
            // X entry is missing
            else if(d.getXEntry().isEmpty() && !d.getYEntry().isEmpty()){
                errors.reject("error.missingXEntry", "X data entry is missing");
            }
            // Both entries are present
            else{
                // Convert X entry to File, scan, and get new length of d.x
                try{
                    FileUtils.writeStringToFile(newX, d.getXEntry());
                    scanFile(newX, d, "X");
                    lengthX = d.getRawX().length;
                }catch(FileNotFoundException e){
                    errors.reject("error.xEntryNotFound", "X entry can't be found");
                }catch(InputMismatchException e){
                    errors.reject("error.xEntryHasNonNumber", "X entry contains non-numbers");
                }catch(IOException e){
                    errors.reject("error.xEntryFailed", "Error processing X entry");
                }

                // Convert Y entry to File, scan, and get new length of d.y
                try{
                    FileUtils.writeStringToFile(newY, d.getYEntry());
                    scanFile(newY, d, "Y");
                    lengthY = d.getRawY().length;
                }catch(FileNotFoundException e){
                    errors.reject("error.yEntryNotFound", "Y entry can't be found");
                }catch(InputMismatchException e){
                    errors.reject("error.yEntryHasNonNumber", "Y entry contains non-numbers");
                }catch(IOException e){
                    errors.reject("error.yEntryFailed", "Error processing Y entry");
                }
            }
        }

        // If the x and y parameters of d have been set due to successful
        // scanning, check number of x and y values
        if(lengthX != null && lengthY != null){
            if(lengthX == 0){
                errors.reject("error.noX", "There are no X values in the submission");
            }
            if(lengthY == 0){
                errors.reject("error.noY", "There are no Y values in th submission");
            }
            if(lengthX > 1000 || lengthY > 1000){
                errors.reject("error.tooManyValues", "Data must have fewer than 1000 examples");
            }
            if(lengthX > lengthY){
                errors.reject("error.tooFewY", "There are more X than Y values");
            }else if(lengthY > lengthX){
                errors.reject("error.tooFewX", "There are more Y than X values");
            }else if(lengthX == lengthY){
                // Set the numPoints of the DataSubmissionDTO
                d.setNumPoints(lengthX);
            }
        }

        // Name, xLabel, yLabel, itemLabel missing
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "Name of dataset is missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "xLabel", "error.xLabel", "Label for X data is missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yLabel", "error.yLabel", "Label for Y data is missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemLabel", "error.itemLabel", "Label for item per example is missing");

        // Delete both Files
        newX.delete();
        newY.delete();
        
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
            submission.setRawX(theVals);
        }else if(xOrY.equals("Y")){
            submission.setRawY(theVals);
        }
    }

}
