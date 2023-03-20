package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.estore.api.estoreapi.model.Enums.*;
public class User {
    @JsonProperty("username")
    private final String username;
    @JsonProperty("id")
    private final int id;
    @JsonProperty("userType")
    private final UserType userType;

    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("id") int id ){
        this.username = username;
        this.id = id;
        if(username == "admin"){
            userType = UserType.Admin;
        }
        else{
            userType = UserType.Customer;
        }
    }
    /**
     * Method to get the username of a user.
     * 
     * @return Returns the string username of the user.
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Method to get the user ID number.
     * 
     * @return returns the id number of a user.
     */
    public int getId(){
        return this.id;
    }

    /**s
     * Method to get the UserAccessLevel of the user.
     * 
     * @return Returns the USerAccessLevel of the user.
     */
    public UserType getUserType(){
        return this.userType;
    }
    
}