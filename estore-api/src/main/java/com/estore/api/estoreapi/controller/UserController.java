//object: user if user.getUserType == UserType.Admin do "display admin page" else display customer
package com.estore.api.estoreapi.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.estore.api.estoreapi.persistence.UserDAO;

@RestController
@RequestMapping("login")
public class UserController{
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDao;

    public UserController(UserDAO userDao){
        this.userDao = userDao;
    }

    // @RequestMapping("id")
    // public ResponseEntity<User> getUser(@PathVariable int id){
    //     LOG.info("GET /login/" + id);
    // }

    //post call to login
    @PostMapping("")
    public ResponseEntity<User> login(@RequestBody String username){
        LOG.info("POST /login " + username);
        try{
            // create user if not already existing
            if(userDao.getUser(username) == null){
                userDao.createUser(username);
            }
            User user = userDao.getUser(username);
            if(user.getUserType() == UserType.Admin){
                // login to admin page
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
            else{
                // login to user page
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}