package com.wordpress.boxofcubes.machinelearningapp.controllers;

import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataController {
    @GetMapping("home")
    public String showHome(){
        return "home";
    }

    @GetMapping("submit-data")
    public String showSubmit(Model model){
        model.addAttribute("data", new Data());
        return "submit";
    }
    @PostMapping("submit-data")
    public String processSubmit(@ModelAttribute @Valid Data data, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("uploaded", true);
            return "submit";
        }
        return "redirect:/set-parameters";
    }
}