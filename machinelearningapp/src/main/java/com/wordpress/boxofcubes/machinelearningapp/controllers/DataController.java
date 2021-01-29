package com.wordpress.boxofcubes.machinelearningapp.controllers;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.data.DataRepository;
import com.wordpress.boxofcubes.machinelearningapp.data.DataValueRepository;
import com.wordpress.boxofcubes.machinelearningapp.data.SavingModelRepository;
import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.DataValue;
import com.wordpress.boxofcubes.machinelearningapp.models.LinearRegression;
import com.wordpress.boxofcubes.machinelearningapp.models.Parameters;
import com.wordpress.boxofcubes.machinelearningapp.models.SavingModel;
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
    /*@Autowired
    DataValueRepository dataValueRepository;*/
    @Autowired
    DataRepository dataRepository;
    @Autowired
    DataSubmissionDTOValidator submissionValidator;
    @Autowired
    ParametersDTOValidator parametersValidator;
    @Autowired
    SavingModelRepository modelRepository;

    @GetMapping("home")
    public String showHome(HttpServletRequest request){
        // If there's a Data object in the session, get rid of it
        request.getSession().removeAttribute("data");
        System.out.println("Deleted Data object from session.");
        
        return "data/home";
    }

    @GetMapping("submit-data")
    public String showSubmit(Model model, HttpSession session){
        model.addAttribute("dataSubmissionDTO", new DataSubmissionDTO());
        //System.out.println("---At submit-data");
        return "data/submit";
    }
    @PostMapping("submit-data/upload")
    public String processUploadData(DataSubmissionDTO dataSubmissionDTO, BindingResult bindingResult, Model model, HttpServletRequest request){
        // Validate data upload submission
        submissionValidator.validate(dataSubmissionDTO, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("uploaded", true);
            return "data/submit";
        }

        // Convert the DTO to a new Data object
        Data data = new Data(dataSubmissionDTO);

        // Make a unique identifier for it and add it to the session
        String dataUUID = UUID.randomUUID().toString();
        request.getSession().setAttribute(dataUUID, data);

        // Also set the Data object itself in the session
        request.getSession().setAttribute("data", data);
        //setDataInSession(request.getSession(), data);

        return "redirect:/view-data?dataUUID="+dataUUID;
    }
    @PostMapping("submit-data/enter")
    public String processEnterData(DataSubmissionDTO dataSubmissionDTO, BindingResult bindingResult, Model model, HttpServletRequest request){
        // Validate data entry submission
        submissionValidator.validate(dataSubmissionDTO, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("entered", true);
            return "data/submit";
        }

        // Convert the DTO to a new Data object
        Data data = new Data(dataSubmissionDTO);

        // Make a unique identifier and set attribute
        String dataUUID = UUID.randomUUID().toString();
        request.getSession().setAttribute(dataUUID, data);

        // Also set the Data object in session
        request.getSession().setAttribute("data", data);
        //setDataInSession(request.getSession(), data);
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

        // Also set the Data object in the session
        request.getSession().setAttribute("data", data);
        //setDataInSession(request.getSession(), data);

        //return "redirect:/view-data";
        return "redirect:/view-data?dataUUID="+dataUUID;
    }

    @PostMapping("retrieve-saved-data")
    public String processSavedData(@RequestParam int id, HttpServletRequest request){
        // Get the Data object from the repository via its ID
        Optional<Data> data = dataRepository.findById(id);
        if(data.isPresent()){
            // Set the Data object in the session
            request.getSession().setAttribute("data", data.get());

            // Make new UUID for it and add it to session
            String dataUUID = UUID.randomUUID().toString();
            request.getSession().setAttribute(dataUUID, data.get());
            //System.out.println("Retrieved saved data "+dataUUID);

            return "redirect:/view-data?dataUUID="+dataUUID;
        }/*else{
            System.out.println("couldn't find saved data");
            return "redirect:/user/account";
        }*/
        return "redirect:/user/account";
    }


    @GetMapping("view-data")
    public String showChart(Model model, @RequestParam String dataUUID, HttpServletRequest request,
    @RequestParam(required=false) Boolean saved){
        // Get the data UUID from the URL and add to the model
        model.addAttribute("dataUUID", dataUUID);

        // If the user has saved the Data object, add a confirmation message
        if(saved != null){
            model.addAttribute("saved", "Dataset is saved to your account");
        }

        //System.out.println("UUID at view-data: "+dataUUID);

        return "data/chart";
    }

    @PostMapping("view-data/return")
    public String returnToViewData(HttpServletRequest request){
        // Get the Data object from the session
        Data data = (Data)request.getSession().getAttribute("data");

        // Make a new UUID for it
        String dataUUID = UUID.randomUUID().toString();

        if(data != null){
            request.getSession().setAttribute(dataUUID, data);
            System.out.println("Returning to view data with obj "+dataUUID);
            return "redirect:/view-data?dataUUID="+dataUUID;
        }else{
            System.out.println("Couldn't find data object to go back to view!");
            return "redirect:/set-parameters";
        }
    }





    @GetMapping("set-parameters")
    public String showParameters(Model model, HttpServletRequest request){
        // Make a new ParametersDTO with the default parameters
        ParametersDTO p = ParametersDTO.getDefaultParameters();

        // Get the Data object from the session
        Data data = (Data)request.getSession().getAttribute("data");
        if(data != null){
            // Set the numPointsInData of the Parameters object
            p.setNumPointsInData(data.getNumPoints());
        }else{
            System.out.println("No data object for setting num points in Parameters!");
        }
        model.addAttribute("parametersDTO", p);

        return "data/parameters";
    }
    @PostMapping("set-parameters")
    public String processParametersTrainModelShowChart(ParametersDTO parametersDTO, BindingResult bindingResult, HttpServletRequest request, Model model){
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

        // Get the Data object from the session 
        Data data = (Data)request.getSession().getAttribute("data");
        if(data != null){
            // Make a new Linear Regression object and train a model
            LinearRegression lr = new LinearRegression(data, parameters);
            lr.trainModel();

            // Add the Linear Regression object to the session
            request.getSession().setAttribute("linearRegression", lr);

            // Make a new UUID for the Data object
            String dataUUID = UUID.randomUUID().toString();
            request.getSession().setAttribute(dataUUID, data);

            // Get the predicted points from the model, add to session
            double[] predictions = lr.getPredictedPoints(data);
            request.getSession().setAttribute("predictions", predictions);

            return "redirect:/trained-model?dataUUID="+dataUUID;
        }else{
            System.out.println("ERROR - couldn't find data for training!");
            return "data/parameters";
        }


    }

    @GetMapping("trained-model")
    public String showTrainedModel(@RequestParam String dataUUID,HttpServletRequest request, Model model){
        // Get the Linear Regression object
        LinearRegression lr = (LinearRegression)request.getSession().getAttribute("linearRegression");

        model.addAttribute("lr", lr);
        model.addAttribute("p", lr.getParameters());
        String costs = "";
        for(double c : lr.getCostsWhileTraining()){
            costs += String.format("%.2f", c)+ "\n";
        }
        model.addAttribute("costs", costs);

        model.addAttribute("dataUUID", dataUUID);

        //
        System.out.println("dataUUID at trained-model: "+dataUUID);
        Data dabs = (Data)request.getSession().getAttribute(dataUUID);
            System.out.println("Data object with UUID? "+(dabs != null));
            //

        return "data/trained";
    }

    @PostMapping("trained-model/return")
    public String returnToTrainedModel(HttpServletRequest request){
        // Get the Data object from the session
        Data data = (Data)request.getSession().getAttribute("data");
        // Make a new UUID for it
        String dataUUID = UUID.randomUUID().toString();

        LinearRegression lr = (LinearRegression)request.getSession().getAttribute("linearRegression");
        if(lr != null){
            double[] predictions = lr.getPredictedPoints(data);
            request.getSession().setAttribute("predictions", predictions);
        }else{
            System.out.println("couldn't find linear regression!");
        }

        if(data != null){
            request.getSession().setAttribute(dataUUID, data);
            System.out.println("Returning to trained model with obj "+dataUUID);
            return "redirect:/trained-model?dataUUID="+dataUUID;
        }else{
            System.out.println("Couldn't find data object to go back to trained model!");
            return "redirect:/test-model";
        }
    }


    @PostMapping("retrieve-saved-model")
    public String processSavedModel(@RequestParam int id, HttpServletRequest request){
        // Get the SavingModel object from the repository via its ID
        Optional<SavingModel> theModel = modelRepository.findById(id);
        if(theModel.isPresent()){
            // Make a new LinearRegression object using the model
            LinearRegression lr = new LinearRegression(theModel.get());

            // Set the LinearRegression object in the session
            request.getSession().setAttribute("linearRegression", lr);

            // Find the Data object that the model is associated with
            Optional<Data> data = dataRepository.findByModels(theModel.get());
            if(data.isPresent()){
                //Set the data object in the session
                request.getSession().setAttribute("data", data.get());

                return "redirect:/test-model";
            }else{
                System.out.println("Can't find data object associated with model!");
            }
        }
        return "redirect:/user/account";
    }

    @GetMapping("test-model")
    public String showTestedModel(HttpServletRequest request, Model model, @RequestParam(required=false) Boolean saved){
        // If the user has saved the model, give confirmation
        if(saved != null){
            model.addAttribute("saved", "Model and dataset are saved to your account");
        }
        LinearRegression lr = (LinearRegression)request.getSession().getAttribute("linearRegression");
        model.addAttribute("lr", lr);
        return "data/tested";
    }

    @GetMapping("predict")
    public String showPredict(HttpServletRequest request, Model model){
        // Get the model
        LinearRegression lr = (LinearRegression)request.getSession().getAttribute("linearRegression");
        // Get the dataset
        Data data = (Data)request.getSession().getAttribute("data");
        if(data != null && lr != null){
            // Get the predictions for the chart
            double[] predictions = lr.getPredictedPoints(data);
            request.getSession().setAttribute("predictions", predictions);

            // Make a new UUID
            String dataUUID = UUID.randomUUID().toString();
            request.getSession().setAttribute(dataUUID, data);
            model.addAttribute("dataUUID", dataUUID);
        }else{
            System.out.println("missing data or model!");
        }

        // If the user has requested a prediction, get it from the session
        Double predictedY = (Double)request.getSession().getAttribute("predictedY");
        if(predictedY != null){
            model.addAttribute("predictedY", predictedY);
            // Remove that prediction from the session
            request.getSession().removeAttribute("predictedY");
        }
        return "data/predict";
    }
    @PostMapping("predict")
    public String processPredict(@RequestParam String x, HttpServletRequest request, Model model){
        // Validate that a number has been entered
        if(x.isEmpty() || x.isBlank()){
            model.addAttribute("error", "Please enter an X value");
            // Get the info for the chart
            LinearRegression lr = (LinearRegression)request.getSession().getAttribute("linearRegression");
            Data data = (Data)request.getSession().getAttribute("data");
            if(data != null && lr != null){
                double[] predictions = lr.getPredictedPoints(data);
                request.getSession().setAttribute("predictions", predictions);
                String dataUUID = UUID.randomUUID().toString();
                request.getSession().setAttribute(dataUUID, data);
                model.addAttribute("dataUUID", dataUUID);
            }
            return "data/predict";
        }
        double xVal;
        try{
            xVal = Double.parseDouble(x);
        }catch(NumberFormatException e){
            model.addAttribute("error", "Please enter a number");
            // Get the info for the chart
            LinearRegression lr = (LinearRegression)request.getSession().getAttribute("linearRegression");
            Data data = (Data)request.getSession().getAttribute("data");
            if(data != null && lr != null){
                double[] predictions = lr.getPredictedPoints(data);
                request.getSession().setAttribute("predictions", predictions);
                String dataUUID = UUID.randomUUID().toString();
                request.getSession().setAttribute(dataUUID, data);
                model.addAttribute("dataUUID", dataUUID);
            }
            return "data/predict";
        }
        
        // Get the model
        LinearRegression lr = (LinearRegression)request.getSession().getAttribute("linearRegression");
        if(lr != null){
            // Make the prediction and add it to the session
            Double predictedY = lr.predict(xVal);
            request.getSession().setAttribute("predictedY", predictedY);
            return "redirect:/predict";
        }else{
            System.out.println("missing linear regression!");
        }
        return "redirect:/predict";
    }

















    /** Converts a DataSubmissionDTO to a new Data object */
    /*public Data getDataObject(DataSubmissionDTO dataSubmissionDTO){
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
    }*/
    /*public static void setDataInSession(HttpSession session, Data data){
        session.setAttribute("data", data);
    }
    public Data getDataFromSession(HttpSession session){
        Data data = (Data)session.getAttribute("data");
        if(data == null){
            return null;
        }
        return data;
    }*/




}