package com.wordpress.boxofcubes.machinelearningapp.controllers;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.data.DataRepository;
import com.wordpress.boxofcubes.machinelearningapp.data.DataValueRepository;
import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.DataValue;
import com.wordpress.boxofcubes.machinelearningapp.models.LinearRegression;
import com.wordpress.boxofcubes.machinelearningapp.models.Parameters;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.DataSubmissionDTO;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.ParametersDTO;
import com.wordpress.boxofcubes.machinelearningapp.validation.DataSubmissionDTOValidator;
import com.wordpress.boxofcubes.machinelearningapp.validation.ParametersDTOValidator;

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
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DataController {
    @Autowired
    DataValueRepository dataValueRepository;
    @Autowired
    DataRepository dataRepository;
    @Autowired
    DataSubmissionDTOValidator submissionValidator;
    @Autowired
    ParametersDTOValidator parametersValidator;

    @GetMapping("home")
    public String showHome(){
        return "data/home";
    }

    @GetMapping("submit-data")
    public String showSubmit(Model model, HttpSession session){
        model.addAttribute("dataSubmissionDTO", new DataSubmissionDTO());
        System.out.println("---At submit-data");
        return "data/submit";
    }
    @PostMapping("submit-data/upload")
    public String processUploadData(DataSubmissionDTO dataSubmissionDTO, BindingResult bindingResult, Model model, HttpServletRequest request){
        submissionValidator.validate(dataSubmissionDTO, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("uploaded", true);
            return "data/submit";
        }

        // Convert the DTO to a new Data object
        Data data = getDataObject(dataSubmissionDTO);
        //dataRepository.save(data);

        // Make a unique identifier and set attribute
        String dataUUID = UUID.randomUUID().toString();
        request.getSession().setAttribute(dataUUID, data);

        // Also set the Data object in session
        setDataInSession(request.getSession(), data);

        //return "redirect:/view-data";
        return "redirect:/view-data?dataUUID="+dataUUID;
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

        // Make a unique identifier and set attribute
        String dataUUID = UUID.randomUUID().toString();
        request.getSession().setAttribute(dataUUID, data);

        // Also set the Data object in session
        setDataInSession(request.getSession(), data);
        //return "redirect:/view-data";
        return "redirect:/view-data?dataUUID="+dataUUID;
    }
    @PostMapping("submit-data/sample")
    public String processSampleData(@RequestParam String sampleData, Model model, HttpServletRequest request){
        // Make a unique identifier for the Data object 
        String dataUUID = UUID.randomUUID().toString();

        // Create the sample data set and set it to the UUID
        Data data = new Data();
        if(sampleData.equals("life")){
            data = Data.makeLifeDataset();
            request.getSession().setAttribute(dataUUID, data);
            
        }else if(sampleData.equals("chocolate")){
            data = Data.makeChocolateDataset();
            request.getSession().setAttribute(dataUUID, data);
        }else{
            data = Data.makeBookDataset();
            request.getSession().setAttribute(dataUUID, data);
        }
        // ALSO SET IT IN SESSION SO CAN ACCESS IT AFTER VIEWING !!!!!!!!!!!!!!! 
        setDataInSession(request.getSession(), data);

        //return "redirect:/view-data";
        return "redirect:/view-data?dataUUID="+dataUUID;
    }



    @GetMapping("view-data")
    public String showChart(Model model, @RequestParam String dataUUID, HttpServletRequest r){
        // Get the data UUID from the URL and add to the model
        model.addAttribute("dataUUID", dataUUID);

        Data byUUID = (Data)r.getSession().getAttribute(dataUUID);
        Data byData = (Data)r.getSession().getAttribute("data");

        System.out.println("byUUID and byData same? "+(byUUID.equals(byData)));

        return "data/chart";
    }




    @GetMapping("set-parameters")
    public String showParameters(Model model){
        model.addAttribute("parametersDTO", ParametersDTO.getDefaultParameters());
        return "data/parameters";
    }
    @PostMapping("set-parameters")
    public String processParametersAndTrainModel(ParametersDTO parametersDTO, BindingResult bindingResult, HttpServletRequest request){
        // Validate the parameters
        parametersValidator.validate(parametersDTO, bindingResult);
        if(bindingResult.hasErrors()){
            return "data/parameters";
        }

        // Convert the DTO to a new Parameters object
        double[] theta = new double[2];
        theta[0] = parametersDTO.getTheta0();
        theta[1] = parametersDTO.getTheta1();
        Parameters parameters = new Parameters(parametersDTO.getTrainingProportion(), theta, parametersDTO.getAlpha(), parametersDTO.getLambda(), parametersDTO.getMaxIterations(), parametersDTO.getConvergenceLevel());

        // Add the Parameters object to the session
        /*request.getSession().setAttribute("parameters", parameters);
        return "redirect:/training-model";*/

        // Get the data object
        Data data = (Data)request.getSession().getAttribute("data");
        if(data != null){
            // Make a new Linear Regression object and train a model
            LinearRegression lr = new LinearRegression(data, parameters);
            lr.trainModel();

            // Add the Linear Regression object to the session
            request.getSession().setAttribute("linearRegression", lr);

            return "redirect:/trained-model";
        }else{
            System.out.println("ERROR - couldn't find data for training!");
            return "data/parameters";
        }
    }

    /* WHEN VALIDATING PARAMETERS, MAKE SURE THERE'S AT LEAST 1 EXAMPLE IN THE CV AND TESTING 
    SETS, GIVEN THEIR DATASET AND THE PROPORTION THEY'VE SELECTED. MACHINE LEARNING WILL RUN
    INTO PROBLEMS OTHERWISE!!!!!!!!!!!!!

    AFTER TRAINING, HITTING 'VIEW DATA' FROM SET-PARAMETERS GO BACK TO TRAINED-MODEL!!!

    NOTE: I SHOULD BREAK THE DATASET INTO ITS SUBSETS AFTER IT'S SUBMITTED (BEFORE SETTING
    PARAMETERS), SO THE TRAINING SET REMAINS THE SAME IF YOU GO BACK AND MODIFY THE 
    PARAMETERS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*****************************

    */

    @GetMapping("trained-model")
    public String showTrainedModel(HttpServletRequest request, Model model){
        // Get the Linear Regression object
        LinearRegression lr = (LinearRegression)request.getSession().getAttribute("linearRegression");

        model.addAttribute("lr", lr);
        model.addAttribute("p", lr.getParameters());

        String costs = "";
        for(double c : lr.getCostsWhileTraining()){
            costs += String.format("%.2f", c)+ "\n";
        }
        model.addAttribute("costs", costs);
        return "data/trained";
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