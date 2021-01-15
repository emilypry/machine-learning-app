package com.wordpress.boxofcubes.machinelearningapp.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserSignupDTOValidator implements Validator{
    @Override
    public boolean supports(Class clazz){
        return UserSignupDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors){  
        UserSignupDTO u = (UserSignupDTO)object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", 
        "error.username", "Please enter a username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", 
        "error.password", "Please enter a password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "verifyPassword", 
        "error.verifyPassword", "Please verify password");
    }  
}
