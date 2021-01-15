package com.wordpress.boxofcubes.machinelearningapp.filters;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wordpress.boxofcubes.machinelearningapp.controllers.UserController;
import com.wordpress.boxofcubes.machinelearningapp.data.UserRepository;
import com.wordpress.boxofcubes.machinelearningapp.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationFilter implements HandlerInterceptor {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserController userController; 

    // User need not be logged in to see these pages
    // ADD HERE AS I CREATE MORE PAGES!!!!!!
    // OR, JUST GET RID OF ALL THIS. NOT NECESSARY, SINCE NON-USERS CAN DO EVERYTHING.
    private static final List<String> whitelist = Arrays.asList("/home", "/user/login", "/user/signup",
    "/submit-data", "/view-data", "/set-parameters");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
    Object handler) throws Exception {
        // Need not be logged in to see whitelisted pages
        if(isWhitelisted(request.getRequestURI())) {
            return true;
        }

        HttpSession session = request.getSession();
        User user = userController.getUserFromSession(session);

        // User is logged in, so proceed with request
        if(user != null){
            return true;
        }
        
        // User not logged in, so don't do request
        response.sendRedirect("/home");
        return false;
    }

    @Override
    public void postHandle(
       HttpServletRequest request, HttpServletResponse response, Object handler, 
       ModelAndView modelAndView) throws Exception {}
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
       Object handler, Exception exception) throws Exception {}

    private static boolean isWhitelisted(String path) {
        for (String pathRoot : whitelist) {
           if (path.startsWith(pathRoot)) {
                 return true;
           }
        }
        return false;
    }


}
