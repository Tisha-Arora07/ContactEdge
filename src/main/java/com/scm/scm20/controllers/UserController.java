package com.scm.scm20.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


import com.scm.scm20.services.UserService;



@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger=LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;



    
    //user dashboard page
    @RequestMapping(value="/dashboard")
    public String userDashboard() {
        System.out.println("User Dashboard");
        return "user/dashboard";
    }

    //user profile page
    @RequestMapping(value="/profile")
    public String userProfile(Model model,Authentication authentication) {
      
        return "user/profile";
    }

    
    

    //user add contacts page


    //user view contacts


    //user edit contacts

    //user delete contacts



    //user search contacts

}
