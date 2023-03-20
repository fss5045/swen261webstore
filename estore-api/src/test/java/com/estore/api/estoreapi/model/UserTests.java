package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Enums.UserType;

/**
 * tests the user.java file
 * <p>
 * 
 * @author jl6941@rit.edu
 */
public class UserTests {
    
    @Test
    public void testConstructor(){
        // Setup
        String expectedName = "test";
        int expectedId = 1;

        // Invoke
        User actual = new User(expectedName, expectedId);

        // Analyze
        assertTrue(actual instanceof User);
        assertEquals(expectedId, actual.getId());
        assertEquals(expectedName, actual.getUsername());
    }


    @Test
    public void testGetUsername(){
        // Setup
        String expectedUsername = "user";
        User testUser = new User(expectedUsername, 1);

        // Invoke
        String result = testUser.getUsername();

        // Analyze
        assertEquals(expectedUsername, result);
    }

    @Test
    public void testGetId(){
        // Setup
        int expectedId = 1;
        User testUser = new User("user", expectedId);

        // Invoke
        int result = testUser.getId();

        // Analyze
        assertEquals(expectedId, result);
    }

    @Test
    public void testGetUserTypeCustomer(){
        // Setup
        User testUser = new User("justin", 0);

        // Invoke
        UserType result = testUser.getUserType();

        // Analyze
        assertEquals(UserType.Customer, result);
    }

    @Test
    public void testGetUserTypeAdmin(){
        // Setup
        User testUser = new User("admin", 0);

        // Invoke
        UserType result = testUser.getUserType();

        // Analyze
        assertEquals(UserType.Admin, result);
    }
}