package com.wordpress.boxofcubes.machinelearningapp.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.data.UserRepository;
import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.User;
import com.wordpress.boxofcubes.machinelearningapp.validation.UserLoginDTO;
import com.wordpress.boxofcubes.machinelearningapp.validation.UserLoginDTOValidator;
import com.wordpress.boxofcubes.machinelearningapp.validation.UserSignupDTO;
import com.wordpress.boxofcubes.machinelearningapp.validation.UserSignupDTOValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("user")
public class UserController{
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSignupDTOValidator signupValidator;
    @Autowired
    UserLoginDTOValidator loginValidator;

    @GetMapping("login")
    public String showLogin(Model model){
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "user/login";
    }
    @PostMapping("login")
    public String processLogin(UserLoginDTO userLoginDTO, BindingResult bindingResult){
        loginValidator.validate(userLoginDTO, bindingResult);

        if(bindingResult.hasErrors()){
            return "user/login";
        }

        // START SESSION!!

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
        // Start new session
        setUserInSession(request.getSession(), user);
        
        return "redirect:/home";
    }


    public static void setUserInSession(HttpSession session, User user){
        session.setAttribute("userId", user.getId());
    }

    public User getUserFromSession(HttpSession session){
        Integer userId = (Integer)session.getAttribute("userId");
        if(userId == null){
            return null;
        }
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return null;
        }
        return user.get();
    }



}
