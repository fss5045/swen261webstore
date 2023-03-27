package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

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
    @Test
    public void testGetCart()
    {
        // Setup
        User testUser = new User("justin", 0);
        User testUser2 = new User("admin", 1);

        //Invoke
        ArrayList<Product> cart = testUser.getCart();
        ArrayList<Product> cart2 = testUser2.getCart();

        //Analyze
        assertNotNull(cart);
        assertNull(cart2);
    }
    @Test
    public void testSetCart()
    {
        // Setup
        User testUser = new User("justin", 0);
        Product basketball = new Product(1, "basketball", 100, 20);
        ArrayList<Product> cart = new ArrayList<>();
        cart.add(basketball);
        
        // Invoke
        testUser.setCart(cart);

        // Analyze
        assertEquals(testUser.getCart(),cart);
    }
}