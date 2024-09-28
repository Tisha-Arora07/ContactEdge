package com.scm.scm20.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.helpers.Message;
import com.scm.scm20.helpers.MessageType;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

     @Autowired
    private UserService userService;
     @GetMapping("/")
    public String index(){
        return "redirect:/home";

    }





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
    //contact
    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }
    //this is login page
    //login
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }
    //this is registration page
    //register
    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm=new UserForm();
        //default value bhi daal sktehe
        //userForm.setName("Tisha");
        model.addAttribute("userForm", userForm);

        return "register";
    }
    

    //processing register

    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processRegister( @Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult, HttpSession session ){
        System.out.println("Processing Register");
        //fetch form data
        //Userform is a class in its object we receive the form entered data
        System.out.println(userForm);
        //validate form data
        if(rBindingResult.hasErrors()){
            return "register";
        }
        //save to database

        //userservice
        //UserForm-->User
       // User user=User.builder()
        //.name(userForm.getName())
        //.about(userForm.getAbout())
        //.email(userForm.getEmail())
        //.password(userForm.getPassword())
        //.phoneNumber(userForm.getPhoneNumber())
        //.profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Famaxfireandsecurity.co.uk%2Four-team%2Fattachment%2Fbusinessman-silhouette-as-avatar-or-default-profile-picture%2F&psig=AOvVaw1odA5s2-4NepUifvq_Pn7q&ust=1723472986004000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCLCQm72X7YcDFQAAAAAdAAAAABAV")
        //.build();
        User user=new User();
        user.setName(userForm.getName());
        user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Famaxfireandsecurity.co.uk%2Four-team%2Fattachment%2Fbusinessman-silhouette-as-avatar-or-default-profile-picture%2F&psig=AOvVaw1odA5s2-4NepUifvq_Pn7q&ust=1723472986004000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCLCQm72X7YcDFQAAAAAdAAAAABAV");

        User savedUser=userService.saveUser(user);
        System.out.println("user saved :");
        //message="Registration successfull"


        //add the message
        Message message=Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message",message);
        //redirect to again login page
        return "redirect:/register";
    }

}
