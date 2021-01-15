package com.wordpress.boxofcubes.machinelearningapp.validation;

import java.util.Optional;

import com.wordpress.boxofcubes.machinelearningapp.data.UserRepository;
import com.wordpress.boxofcubes.machinelearningapp.models.User;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.UserSignupDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserSignupDTOValidator implements Validator{
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class clazz){
        return UserSignupDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors){  
        UserSignupDTO u = (UserSignupDTO)object;
        String username = u.getUsername();
        String password = u.getPassword();
        String verify = u.getVerifyPassword();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", 
        "error.username", "Please enter a username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", 
        "error.password", "Please enter a password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "verifyPassword", 
        "error.verifyPassword", "Please verify password");

        // Username errors
        if(!username.isEmpty()){
            // Whitespace
            if(username.contains(" ")){
                errors.reject("error.usernameHasSpace", "Username cannot have any spaces");
            }
            // Size
            if(username.length() < 6 || username.length() > 20){
                errors.reject("error.usernameSize", "Username must be between 6 and 20 characters");
            }

            // User with that username already in database
            // MUST TEST!!!
            Optional<User> anotherUser = userRepository.findByUsername(username);
            if(anotherUser.isPresent()){
                errors.reject("error.usernameSize", "Username is taken");
            }

        }

        // Password errors
        if(!password.isEmpty()){
            // Size
            if(password.length() < 8){
                errors.reject("error.passwordTooShort", "Password must be at least 8 characters");
            }else if(password.length() > 35){
                errors.reject("error.passwordTooLong", "Password must be fewer than 35 characters");
            }

            // Verify
            if(!verify.isEmpty() && !password.equals(verify)){
                errors.reject("error.passwordNotVerified", "Verified password must be the same as password");
            }
        } 
    
    }  
}
