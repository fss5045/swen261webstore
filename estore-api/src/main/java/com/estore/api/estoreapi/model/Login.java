package com.estore.api.estoreapi.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import com.estore.api.estoreapi.model.User;



import com.fasterxml.jackson.annotation.JsonProperty;


public class Login {
    
    private static final Logger log = LoggerFactory.getLogger(Login.class);
    
    @JsonProperty("username")
    private String username;
    private boolean isAdmin;

    public Login(@JsonProperty("username") String username){
        this.username = username;
    }
    
    public boolean checkAdmin(String username){
        this.isAdmin = username.equals("admin");
        return isAdmin;
    }
    
    public ResponseEntity<User> addAdditionalUser(String username){
        log.info("New user added: " + username);
        // add logic to add new user to the database or user list
        User newUser = new User(username);
        return ResponseEntity.ok(newUser);
    }
}
