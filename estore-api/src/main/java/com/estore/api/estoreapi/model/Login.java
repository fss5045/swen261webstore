package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {
    
    @JsonProperty("username") private String username;
    private boolean isAdmin;

    public Login(@JsonProperty("username") String username){
        this.username = username;
    }
    
    public boolean checkAdmin(String username){
        this.isAdmin = username.equals("admin");
        return isAdmin;
    }

}
