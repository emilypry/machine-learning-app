package com.wordpress.boxofcubes.machinelearningapp.controllers;

import javax.validation.Valid; 

import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.DataEntryDTO;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.DataSubmitSharedDTO;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.DataUploadDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DataController {
    @GetMapping("home")
    public String showHome(){
        return "home";
    }

    @GetMapping("submit-data")
    public String showSubmit(Model model){
        model.addAttribute("dataUploadDTO", new DataUploadDTO());
        return "submit";
    }
    @PostMapping("submit-data/upload")
    public String processUploadData(@Valid DataUploadDTO dataUploadDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("uploaded", true);
            return "submit";
        }
        return "redirect:/set-parameters";
    }


    /* Could have /submit form bind to a DataSubmission class, attributes multipart file x and y,
    String x and y, xl, yl, il. Then constructors that take either multipart files or Strings. 
    Post handling for /submit is for valid DataSubmission object, so errors are for DataSubmission
    object. Validator reads multipart file, or String, and returns whether all numbers. (Want to
    be able to change message depending on what type of error.) If no errors, a method is called
    to convert the file/string to a list of doubles. (Where is that method stored?)
        Seems to duplicate efforts of reading through file/string. 
    */
}