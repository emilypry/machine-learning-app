package com.wordpress.boxofcubes.machinelearningapp.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.UserLoginDTO;

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
        if(bindingResult.hasErrors()){
            return "user/login";
        }

        // START SESSION
        return "redirect:/home";

    }




}
