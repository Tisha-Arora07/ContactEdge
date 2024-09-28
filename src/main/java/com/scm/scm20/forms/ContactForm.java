package com.scm.scm20.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.scm20.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {


    @NotBlank(message="Name is required")
    private String name;

   @NotBlank(message="Address is required")
    private String address;
    @NotBlank(message = "Email is required")
    @Email(message="Invalid Email [example@gmail.com]")
    
    private String email;
    @NotBlank(message="Phone Number is Required")
    @Pattern(regexp = "^[0-9]{10}$", message="Invalid Phone Number")
    private String phoneNumber;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
    //annotation create for file validation
    //size
    //resolution

    @ValidFile(message="Invalid File")
    private MultipartFile contactImage;

}
