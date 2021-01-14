package com.wordpress.boxofcubes.machinelearningapp.controllers;

import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.DataSubmissionDTO;
import com.wordpress.boxofcubes.machinelearningapp.validation.DataSubmissionDTOValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DataController {

    @Autowired
    DataSubmissionDTOValidator submissionValidator;

    @GetMapping("home")
    public String showHome(){
        return "home";
    }

    @GetMapping("submit-data")
    public String showSubmit(Model model){
        model.addAttribute("dataSubmissionDTO", new DataSubmissionDTO());
        return "submit";
    }
    @PostMapping("submit-data/upload")
    public String processUploadData(DataSubmissionDTO dataSubmissionDTO, BindingResult bindingResult, Model model){
        submissionValidator.validate(dataSubmissionDTO, bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("uploaded", true);
            System.out.println("Upload error!");
            return "submit";
        }

        Data data = new Data(dataSubmissionDTO.getX(), dataSubmissionDTO.getY(),
        dataSubmissionDTO.getName(), dataSubmissionDTO.getXLabel(), dataSubmissionDTO.getYLabel(),
        dataSubmissionDTO.getItemLabel());
        System.out.println(data.getNumPoints()+" "+data.getName());

        return "redirect:/set-parameters";
    }
    @PostMapping("submit-data/enter")
    public String processEnterData(DataSubmissionDTO dataSubmissionDTO, BindingResult bindingResult, Model model){
        submissionValidator.validate(dataSubmissionDTO, bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("entered", true);
            System.out.println("Entry error!");
            return "submit";
        }

        Data data = new Data(dataSubmissionDTO.getX(), dataSubmissionDTO.getY(),
        dataSubmissionDTO.getName(), dataSubmissionDTO.getXLabel(), dataSubmissionDTO.getYLabel(),
        dataSubmissionDTO.getItemLabel());
        System.out.println(data.getNumPoints()+" "+data.getName());

        return "redirect:/set-parameters";
    }
    @PostMapping("submit-data/sample")
    public String processSampleData(@RequestParam String sampleData, Model model){
        System.out.println(sampleData);

        // Need to get sample Data object from repository for use; or, could
        // just construct them here...

        return "redirect:/set-parameters";
    }

    @GetMapping("set-parameters")
    public String showParameters(){
        return "parameters";
    }
}