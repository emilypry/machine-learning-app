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
    public String processUploadData(DataSubmissionDTO dataSubmissionDTO, BindingResult bindingResult, Model model, HttpServletRequest request){
        submissionValidator.validate(dataSubmissionDTO, bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("uploaded", true);
            System.out.println("Upload error!");
            return "data/submit";
        }

        // Convert the DTO to a new Data object
        Data data = getDataObject(dataSubmissionDTO);
        //dataRepository.save(data);

        // Set the Data object in session
        setDataInSession(request.getSession(), data);

        return "redirect:/view-data";
    }
    @PostMapping("submit-data/enter")
    public String processEnterData(DataSubmissionDTO dataSubmissionDTO, BindingResult bindingResult, Model model, HttpServletRequest request){
        submissionValidator.validate(dataSubmissionDTO, bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("entered", true);
            return "data/submit";
        }

        // Convert the DTO to a new Data object
        Data data = getDataObject(dataSubmissionDTO);
        //dataRepository.save(data);

        // Set the Data object in session
        setDataInSession(request.getSession(), data);

        return "redirect:/view-data";
    }
    @PostMapping("submit-data/sample")
    public String processSampleData(@RequestParam String sampleData, Model model, HttpServletRequest request){
        // Create the sample data set
        if(sampleData.equals("life")){
            setDataInSession(request.getSession(), Data.makeLifeDataset());
        }else if(sampleData.equals("chocolate")){
            setDataInSession(request.getSession(), Data.makeChocolateDataset());
        }

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


    /** Converts a DataSubmissionDTO to a new Data object */
    public Data getDataObject(DataSubmissionDTO dataSubmissionDTO){
        Data data = new Data();

        // RawX and RawY are double[]s - convert them to List of DataValues
        List<DataValue> dataValues = new ArrayList<>();
        for(double x : dataSubmissionDTO.getRawX()){
            dataValues.add(new DataValue(x, data, true));
        }
        for(double y : dataSubmissionDTO.getRawY()){
            dataValues.add(new DataValue(y, data, false));
        }

        data.setDataValues(dataValues);
        data.setNumPoints(dataSubmissionDTO.getNumPoints());
        data.setName(dataSubmissionDTO.getName());
        data.setXLabel(dataSubmissionDTO.getXLabel());
        data.setYLabel(dataSubmissionDTO.getYLabel());
        data.setItemLabel(dataSubmissionDTO.getItemLabel());

        return data;
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