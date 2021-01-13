package com.wordpress.boxofcubes.machinelearningapp.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wordpress.boxofcubes.machinelearningapp.models.dto.DataSubmitSharedDTO;
import com.wordpress.boxofcubes.machinelearningapp.validation.ListOfNumbers;

import org.springframework.validation.Errors;
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
            DataSubmitSharedDTO.convertToNums(newFile);
        }catch(FileNotFoundException e){
            System.out.println(e);
            return false;
        }catch(InputMismatchException e){
            System.out.println(e);
            return false;
        }catch(IOException e){
            System.out.println(e);
            return false;
        }
        return true;
        
    }

}