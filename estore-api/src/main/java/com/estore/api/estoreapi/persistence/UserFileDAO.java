package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.User;

public class UserFileDAO implements UserDAO{
    Map<Integer,User> users;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    public UserFileDAO(@Value("${logins.file}") String filename, ObjectMapper objectMapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User[] getUsers(){
        synchronized(users) {
            return getUsersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User getUser(String username){
        synchronized(users) {
            if (users.containsKey(username))
                return users.get(username);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User createUser(String username) throws IOException{
        synchronized(users) {
            // We create a new product object because the id field is immutable
            // and we need to assign the next unique id
            User newUser = new User(username, nextId);
            users.put(newUser.getId(),newUser);
            save(); // may throw an IOException
            return newUser;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteUser(int id) throws IOException{
        synchronized(users) {
            if (users.containsKey(id)) {
                users.remove(id);
                return save();
            }
            else
                return false;
        }
    }
    
}
