package com.wordpress.boxofcubes.machinelearningapp.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wordpress.boxofcubes.machinelearningapp.validation.ListOfNumbers;

import org.springframework.web.multipart.MultipartFile;

public class ListOfNumbersValidator implements ConstraintValidator<ListOfNumbers, MultipartFile> {

    @Override
    public void initialize(ListOfNumbers ListOfNumbers) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext cxt) {
        if(file.isEmpty()){
            return false;
        }
        try{
            File newFile = new File(file.getOriginalFilename());
            file.transferTo(newFile);
            ArrayList<Double> numbers = new ArrayList<>();
        
            Scanner scan = new Scanner(newFile);
            while(scan.hasNext()){
                numbers.add(scan.nextDouble());
            }
            scan.close();
        }catch(FileNotFoundException e){
            return false;
        }catch(InputMismatchException e){
            return false;
        }catch(IOException e){
            return false;
        }
        return true;
        
    }

}