package com.wordpress.boxofcubes.machinelearningapp.validation;

import java.util.Optional;

import com.wordpress.boxofcubes.machinelearningapp.data.UserRepository;
import com.wordpress.boxofcubes.machinelearningapp.models.User;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.UserLoginDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserLoginDTOValidator implements Validator{
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class clazz){
        return UserLoginDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors){  
        UserLoginDTO u = (UserLoginDTO)object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", 
        "error.username", "Please enter username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", 
        "error.password", "Please enter password");

        if(!u.getUsername().isEmpty() && !u.getPassword().isEmpty()){
            // Make sure the User is in the database
            Optional<User> user = userRepository.findByUsername(u.getUsername());
            if(user.isEmpty()){
                errors.reject("error.usernameNotInDB", "The username is incorrect");
            }else{
                // Make sure the password is correct
                if(user.get().checkPassword(u.getPassword()) == false){
                    errors.reject("error.wrongPassword", "The password is incorrect");
                }

            }
        }

    }
}