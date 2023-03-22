package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.model.Enums.UserType;

@RestController
@RequestMapping("login")
public class LoginController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserController userController;
    private UserType currentUser;

    public LoginController(UserController userController){
        this.userController = userController;
        this.currentUser = null;
    }

    @PostMapping("/login/{username}")
    public ResponseEntity<User> login(@PathVariable String username){
        LOG.info("POST /login " + username);
        User user = null;
        // create user if not already existing
        if(userController.getUser(username).getStatusCode() == HttpStatus.NOT_FOUND){
            user = userController.createUser(username).getBody();
        }
        else{
            user = userController.getUser(username).getBody();
        }
        if(user.getUserType() == UserType.Admin){
            // login to admin page
            LOG.info("logging in as admin");
            this.currentUser = UserType.Admin;
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        else{
            // login to user page
            LOG.info("logging in as customer");
            this.currentUser = UserType.Customer;
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }

    @PatchMapping("/logout")
    public ResponseEntity<Boolean> logout(){
        LOG.info("logging out");
        this.currentUser = null;
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
