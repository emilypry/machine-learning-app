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

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

public class DataLengthMatchesValidator implements ConstraintValidator<DataLengthMatches, Object> {
    
    private String field;
    private String fieldMatch;

    public void initialize(DataLengthMatches constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        
        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }
}
