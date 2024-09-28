package com.scm.scm20.services;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;

import java.util.*;

import org.springframework.data.domain.Page;


public interface ContactService {
//save contact
Contact save(Contact contact);

//update contact
Contact update(Contact contact);

//get contact
List<Contact> getAll();

//get contact by id
Contact getById(String id);

//delete contact
void delete (String id);


//search contact
Page<Contact> searchByName(String nameKeyword,int size,int page,String sortBy,String order,User user);
Page<Contact> searchByEmail(String emailKeyword,int size,int page,String sortBy,String order,User user);
Page<Contact> searchByPhoneNumber(String phoneNumberKeyword,int size,int page,String sortBy,String order,User user);


//get contact by user id
List<Contact> getByUserId(String userId);

Page<Contact> getByUser(User user,int page,int size,String sortField,String sortDirection);

}
