package com.wordpress.boxofcubes.machinelearningapp.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.data.DataRepository;
import com.wordpress.boxofcubes.machinelearningapp.data.ModelRepository;
import com.wordpress.boxofcubes.machinelearningapp.data.SavingModelRepository;
import com.wordpress.boxofcubes.machinelearningapp.data.UserRepository;
import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.LinearRegression;
import com.wordpress.boxofcubes.machinelearningapp.models.SavingModel;
import com.wordpress.boxofcubes.machinelearningapp.models.User;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.UserLoginDTO;
import com.wordpress.boxofcubes.machinelearningapp.validation.UserLoginDTOValidator;
import com.wordpress.boxofcubes.machinelearningapp.models.dto.UserSignupDTO;
import com.wordpress.boxofcubes.machinelearningapp.validation.UserSignupDTOValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("user")
public class UserController{
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSignupDTOValidator signupValidator;
    @Autowired
    UserLoginDTOValidator loginValidator;
    @Autowired
    DataRepository dataRepository;
    @Autowired
    SavingModelRepository savingModelRepository;

    @GetMapping("login")
    public String showLogin(Model model){
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "user/login";
    }
    @PostMapping("login")
    public String processLogin(UserLoginDTO userLoginDTO, BindingResult bindingResult, 
    HttpServletRequest request){
        // Validate user login
        loginValidator.validate(userLoginDTO, bindingResult);
        if(bindingResult.hasErrors()){
            return "user/login";
        }

        // Get user from database
        Optional<User> user = userRepository.findByUsername(userLoginDTO.getUsername());
        if(user.isEmpty()){
            return "user/login";
        }
        // Add user to session
        request.getSession().setAttribute("user", user.get());
        //setUserInSession(request.getSession(), user.get());
        return "redirect:/home";
    }

    @GetMapping("signup")
    public String showSignup(Model model){
        model.addAttribute("userSignupDTO", new UserSignupDTO());
        return "user/signup";
    }
    @PostMapping("signup")
    public String processSignup(UserSignupDTO userSignupDTO, BindingResult bindingResult,
    Model model, HttpServletRequest request){
        // Validate user signup
        signupValidator.validate(userSignupDTO, bindingResult);
        if(bindingResult.hasErrors()){
            return "user/signup";
        }

        // Create new user
        User user = new User(userSignupDTO.getUsername(), userSignupDTO.getPassword());
        // Add to database
        userRepository.save(user);
        // Add user to session
        request.getSession().setAttribute("user", user);
        //setUserInSession(request.getSession(), user);
        
        return "redirect:/home";
    }

    @GetMapping("account")
    public String showAccount(){
        return "user/account";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/home";
    }

    @PostMapping("/save-data")
    public String processSaveData(HttpServletRequest request, @RequestParam String dataUUID){
        Data data = (Data)request.getSession().getAttribute("data");
        User user = (User)request.getSession().getAttribute("user");

        if(data != null && user != null && !dataUUID.isEmpty()){
            // Get rid of the old dataUUID attribute
            request.getSession().removeAttribute(dataUUID);

            // Make a new dataUUID attribute
            dataUUID = UUID.randomUUID().toString();
            request.getSession().setAttribute(dataUUID, data);

            // Make sure the dataset isn't already saved
            Optional<Data> inDatabase = dataRepository.findById(data.getId());
            if(inDatabase.isPresent()){
                System.out.println(" dataset already saved to user account!");
                return "redirect:/view-data?dataUUID="+dataUUID+"&saved=true";
            }

            // Save the dataset
            data.setUser(user);
            dataRepository.save(data);
            System.out.println("saved dataset to user account!");
            
            return "redirect:/view-data?dataUUID="+dataUUID+"&saved=true";
        }else{
            System.out.println("data or user or oldUUID missing!");
            return "redirect:/view-data?dataUUID="+dataUUID;
        }
    }

    @PostMapping("/save-model")
    public String processSaveModel(HttpServletRequest request/*, @RequestParam String dataUUID*/){
        Data data = (Data)request.getSession().getAttribute("data");
        User user = (User)request.getSession().getAttribute("user");
        LinearRegression lr = (LinearRegression)request.getSession().getAttribute("linearRegression");

        if(data != null && user != null && lr != null /*&& !dataUUID.isEmpty()*/){
            // Get rid of the old dataUUID attribute
            //request.getSession().removeAttribute(dataUUID);

            // Make a new dataUUID attribute
            //dataUUID = UUID.randomUUID().toString();
            //request.getSession().setAttribute(dataUUID, data);

            // Make a Model object from the Linear Regression object
            SavingModel thisModel = new SavingModel(lr);

            // Set the Data object associated with the model
            thisModel.setData(data);
           
            // See if the Data object is already in the database
            Optional<Data> inDB = dataRepository.findById(data.getId());
            if(inDB.isPresent()){
                // If so, save only the model
                savingModelRepository.save(thisModel);
                System.out.println("data already saved - adding model");
                
            }else{
                // If not, set the Data object's user, add the model, and save 
                data.setUser(user);
                data.addModel(thisModel);
                dataRepository.save(data);

                System.out.println("saved data with its model");
            }
            
            return "redirect:/test-model?saved=true";
        }else{
            System.out.println("data or user or linear regression missing!");
            return "redirect:/test-model";
        }
    }


}
