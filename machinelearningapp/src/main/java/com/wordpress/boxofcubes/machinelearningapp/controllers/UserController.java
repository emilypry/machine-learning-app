package com.wordpress.boxofcubes.machinelearningapp.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.validation.UserLoginDTO;
import com.wordpress.boxofcubes.machinelearningapp.validation.UserSignupDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("user")
public class UserController{
    @GetMapping("login")
    public String showLogin(Model model){
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "user/login";
    }
    @PostMapping("login")
    public String processLogin(@Valid UserLoginDTO userloginDTO, BindingResult bindingResult){
        // Check if the username is in repository

        // Check if the password is correct

        if(bindingResult.hasErrors()){
            return "user/login";
        }

        // START SESSION!!

        return "redirect:/home";

    }

    @GetMapping("signup")
    public String showSignup(Model model){
        model.addAttribute("userSignupDTO", new UserSignupDTO());
        return "user/signup";
    }
    @PostMapping("signup")
    public String processSignup(@Valid UserSignupDTO userSignupDTO, BindingResult bindingResult,
    Model model){

        // Make sure username meets constraints
        
        // Make sure no user with that username in database

        // Make sure password meets constraints 

        if(bindingResult.hasErrors()){
            return "user/signup";
        }

        // START SESSION!!

        return "redirect:/home";
    }




}
