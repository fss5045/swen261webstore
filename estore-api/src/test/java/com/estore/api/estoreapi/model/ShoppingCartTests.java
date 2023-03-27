package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;


/**
 * tests the shoppingcart file
 * <p>
 * 
 * @author jl6941@rit.edu
 */
public class ShoppingCartTests{
    private ProductDAO productDAO;  

    @Test
    public void testConstructor(){
        // Setup
        String expectedUsername = "test";
        int expectedId = 1;

        // Invoke
        ShoppingCart result = new ShoppingCart(productDAO);

        // Analyze
        assertEquals(ShoppingCart.class, result.getClass());
    }

    
    public class testCart {
      
      @Test
      public void testAddItem() throws IOException {
        // Create a User object
        User user = new User("bob",1);
        // Create a Product object
        Product product = new Product(10, "product1", 1, 10);

        ShoppingCart cart = new ShoppingCart(productDAO);
        // Add the Product object to the User's cart
        user.getCart().add(product);
        // Call the addItem method of the Cart object with the User object and the product ID
        cart.addItem(user, 1);
        // Check that the Product object was added to the User's cart
        assertTrue(user.getCart().contains(product));
      }
    
}


@Test
  public void testRemoveItem() throws IOException {
    // Create a User object
    User user = new User("bob", 1);
    // Create a Product object
    Product product = new Product(10,"product1",1,10);

    ShoppingCart cart = new ShoppingCart(productDAO);
    // Add the Product object to the User's cart
    user.getCart().add(product);
    // Call the removeItem method of the Cart object with the User object and the product ID
    cart.removeItem(user, 1);
    // Check that the Product object was removed from the User's cart
    assertFalse(user.getCart().contains(product));
  }
}
