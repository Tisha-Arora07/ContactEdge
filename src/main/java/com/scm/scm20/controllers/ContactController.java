package com.scm.scm20.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.forms.ContactSearchForm;
import com.scm.scm20.helpers.AppConstants;
import com.scm.scm20.helpers.Helper;
import com.scm.scm20.helpers.Message;
import com.scm.scm20.helpers.MessageType;
import com.scm.scm20.services.ContactService;
import com.scm.scm20.services.ImageService;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
     @Autowired
    private ImageService imageService;

    private Logger logger=org.slf4j.LoggerFactory.getLogger(ContactController.class);
    @Autowired
    private ContactService contactService;

     @Autowired
    private UserService userService;

    private User userByEmail;

    @RequestMapping("/add")
    public String addContactView(Model model){
        //add the contact : handler
        ContactForm contactForm=new ContactForm();
       
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String saveContact( @Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication,HttpSession session){
        //process the form data

        //validate form
        
        if(result.hasErrors()){
            result.getAllErrors().forEach(error-> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
            .content("Please correct the following errors")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }
        
        String username=Helper.getEmailOfLoggedInUser(authentication);


        //form-->contact
        User user = userService.getUserByEmail(username);
        //process the contact picture

       // image processing
       logger.info("file information : {}",contactForm.getContactImage().getOriginalFilename());
       //upload code


       String filename=UUID.randomUUID().toString();
       String fileURL=imageService.uploadImage(contactForm.getContactImage(),filename);



        Contact contact=new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setFavorite(contactForm.isFavorite());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setDescription(contactForm.getDescription());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setUser(user);
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setAddress(contactForm.getAddress());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);


        contactService.save(contact);
        System.out.println(contactForm);

        //set the contact picture url


        //set the message to be displayed on the view
        session.setAttribute("message",Message.builder()
        .content("New Contact is successfully added!")
        .type(MessageType.green)
        
        .build());
        return "redirect:/user/contacts/add";

    } 
    



    //view contacts
    @RequestMapping
    public String viewContacts(
        @RequestParam(value="page",defaultValue = "0") int page,
        @RequestParam(value="size", defaultValue = AppConstants.PAGE_SIZE +"") int size,
        @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value="direction",defaultValue = "asc") String direction

        
    ,Model model,Authentication authentication){
        //load all the user contacts
        String username=Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(username);
       Page<Contact> pageContact= contactService.getByUser(user,page,size,sortBy,direction);
       model.addAttribute("pageContact", pageContact);
       model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

       model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";

    }



    //search handler
    @RequestMapping("/search")
    public String searchHandler(
      @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value="size",defaultValue = AppConstants.PAGE_SIZE + "") int size,
        @RequestParam(value="page" , defaultValue = "0") int page,
        @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value="direction",defaultValue = "asc") String direction,
        Model model,
        Authentication authentication
    ){
        logger.info("field {} keyword {}",contactSearchForm.getField(),contactSearchForm.getValue());


        var user=userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
       Page<Contact> pageContact=null;
       if(contactSearchForm.getField().equalsIgnoreCase("name")){
        pageContact=contactService.searchByName(contactSearchForm.getValue(),size,page,sortBy,direction,user);
       }
       else if(contactSearchForm.getField().equalsIgnoreCase("email")){
        pageContact=contactService.searchByEmail(contactSearchForm.getValue(),size,page,sortBy,direction,user);
       }
       else if(contactSearchForm.getField().equalsIgnoreCase("phone")){
        pageContact=contactService.searchByPhoneNumber(contactSearchForm.getValue(),size,page,sortBy,direction,user);
       }
       logger.info("pageContact {}",pageContact);

       model.addAttribute("contactSearchForm", contactSearchForm);
       model.addAttribute("pageContact", pageContact);
       model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }
}
