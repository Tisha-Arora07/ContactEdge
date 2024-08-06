package com.scm.scm20.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/home")
    
    public String home(Model model){
        System.out.println("Home Page Handler");
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("youtubeChannel", "Learn code with durgesh");
        model.addAttribute("githubRepo", "https://github.com/Tisha-Arora07");
        return "home";
    }
    //about route
    @RequestMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin",true);
        System.out.println("About Page loading");
        return "about";
    }
    //services
    @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("Services Page loading");
        return "services";
    }

}
