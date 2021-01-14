package com.wordpress.boxofcubes.machinelearningapp.controllers;

import javax.servlet.http.HttpSession;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController{
    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }




}
