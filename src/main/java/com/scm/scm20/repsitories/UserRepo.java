package com.scm.scm20.repsitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.scm20.entities.User;

import java.util.Optional;



@Repository
public interface UserRepo extends JpaRepository<User,String> {
    //extra method db related operations
    //custom query methods
    //custom finder methods

    Optional<User>  findByEmail(String email);
    Optional<User>  findByEmailAndPassword(String email, String password);

}
