package com.wordpress.boxofcubes.machinelearningapp.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wordpress.boxofcubes.machinelearningapp.data.UserRepository;
import com.wordpress.boxofcubes.machinelearningapp.models.Data;
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

@Controller
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


    /*public static void setUserInSession(HttpSession session, User user){
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
    }*/

    // MUST TEST!!!!!!!!!!
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/home";
    }


}
