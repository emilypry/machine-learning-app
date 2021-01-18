package com.wordpress.boxofcubes.machinelearningapp.controllers;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.data.DataRepository;
import com.wordpress.boxofcubes.machinelearningapp.data.DataValueRepository;
import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.DataValue;
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
    DataValueRepository dataValueRepository;
    @Autowired
    DataRepository dataRepository;
    @Autowired
    DataSubmissionDTOValidator submissionValidator;

    @GetMapping("home")
    public String showHome(){
        return "data/home";
    }

    @GetMapping("submit-data")
    public String showSubmit(Model model){
        model.addAttribute("dataSubmissionDTO", new DataSubmissionDTO());
        return "data/submit";
    }
    @PostMapping("submit-data/upload")
    public String processUploadData(DataSubmissionDTO dataSubmissionDTO, BindingResult bindingResult, Model model){
        submissionValidator.validate(dataSubmissionDTO, bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("uploaded", true);
            System.out.println("Upload error!");
            return "data/submit";
        }

        /*Data data = new Data(dataSubmissionDTO.getX(), dataSubmissionDTO.getY(),
        dataSubmissionDTO.getName(), dataSubmissionDTO.getXLabel(), dataSubmissionDTO.getYLabel(),
        dataSubmissionDTO.getItemLabel());
        System.out.println(data.getNumPoints()+" "+data.getName());*/

        return "redirect:/view-data";
    }
    @PostMapping("submit-data/enter")
    public String processEnterData(DataSubmissionDTO dataSubmissionDTO, BindingResult bindingResult, Model model){
        submissionValidator.validate(dataSubmissionDTO, bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("entered", true);
            return "data/submit";
        }

        // The rawX and rawY attributes of dataSubmissionDTO are double[]s; 
        // Convert the values to DataValues and save to dataValueRepository
        // Then set to dataValues attribute of Data object
        Data data = new Data();
        List<DataValue> dataValues = new ArrayList<>();
        for(double x : dataSubmissionDTO.getRawX()){
            dataValues.add(new DataValue(x, data, true));
        }
        for(double y : dataSubmissionDTO.getRawY()){
            dataValues.add(new DataValue(y, data, false));
        }
        /*Data data = new Data(dataValues, dataSubmissionDTO.getName(), dataSubmissionDTO.getXLabel(), dataSubmissionDTO.getYLabel(), dataSubmissionDTO.getItemLabel());*/
        data.setDataValues(dataValues);
        data.setNumPoints(dataSubmissionDTO.getNumPoints());
        data.setName(dataSubmissionDTO.getName());
        data.setXLabel(dataSubmissionDTO.getXLabel());
        data.setYLabel(dataSubmissionDTO.getYLabel());
        data.setItemLabel(dataSubmissionDTO.getItemLabel());

        System.out.println(data.getNumPoints()+" "+data.getName());

        dataRepository.save(data);

        //setDataInSession(request.getSession(), data);)

        return "redirect:/view-data";
    }
    @PostMapping("submit-data/sample")
    public String processSampleData(@RequestParam String sampleData, Model model){
        System.out.println(sampleData);

        // Need to get sample Data object from repository for use; or, could
        // just construct them here...

        return "redirect:/view-data";
    }

    @GetMapping("view-data")
    public String showChart(){
        return "data/chart";
    }

    @GetMapping("set-parameters")
    public String showParameters(){
        return "data/parameters";
    }



    public static void setDataInSession(HttpSession session, Data data){
        session.setAttribute("data", data);
    }
    public Data getDataFromSession(HttpSession session){
        Data data = (Data)session.getAttribute("data");
        if(data == null){
            return null;
        }
        return data;
    }
}