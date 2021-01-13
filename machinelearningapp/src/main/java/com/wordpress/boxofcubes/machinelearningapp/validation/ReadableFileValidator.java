package com.wordpress.boxofcubes.machinelearningapp.validation;

import org.springframework.validation.Validator;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

public class ReadableFileValidator implements Validator{
    @Override
    public boolean supports(Class clazz) {
        return MultipartFile.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors e){
        MultipartFile uploadedFile = (MultipartFile)o;
        if(uploadedFile.isEmpty()){
            e.reject("File must not be empty");
        }
        
    }
}
