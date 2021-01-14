package com.wordpress.boxofcubes.machinelearningapp.controllers;

import javax.servlet.http.HttpSession;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;

import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController{

    private static final String dataSessionKey = "data";



    private static void setDataInSession(HttpSession session, Data data){
        session.setAttribute(dataSessionKey, data);
    }
}
