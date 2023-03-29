package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import javax.lang.model.type.NullType;

import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.model.Enums.UserType;

@RestController
@RequestMapping("")
public class LoginController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserController userController;
    public User currentUser;

    public LoginController(UserController userController){
        this.userController = userController;
        this.currentUser = null;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody String username){
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
            this.currentUser = user;
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        else{
            // login to user page
            LOG.info("logging in as customer:" + username);
            this.currentUser = user;
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }

    @PatchMapping("/logout")
    public ResponseEntity<Boolean> logout(){
        LOG.info("logging out");
        this.currentUser = null;
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(){
        LOG.info("GET /current");
        if(currentUser != null){
            LOG.info("current user: " + currentUser.getUsername());
            return new ResponseEntity<User>(currentUser, HttpStatus.OK);
        }
        return new ResponseEntity<User>(new User(), HttpStatus.OK);
    }
}
