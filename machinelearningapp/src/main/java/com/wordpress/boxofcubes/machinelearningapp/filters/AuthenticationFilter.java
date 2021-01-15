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

public class AuthenticationFilter implements HandlerInterceptor {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserController userController; 

    // User need not be logged in to see these pages
    // ADD HERE AS I CREATE MORE PAGES!!!!!!
    private static final List<String> whitelist = Arrays.asList("/home", "/submit-data",
                        "/view-data", "/set-parameters");

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



    private static boolean isWhitelisted(String path) {
        for (String pathRoot : whitelist) {
           if (path.startsWith(pathRoot)) {
                 return true;
           }
        }
        return false;
    }


}
