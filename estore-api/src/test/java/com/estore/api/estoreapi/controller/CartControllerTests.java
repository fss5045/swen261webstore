package com.estore.api.estoreapi.controller;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CartControllerTests {
    private ProductDAO testProductDAO;
    private LoginController testLoginController;
    private ShoppingCart testShoppingCart;
    private CartController testCartController;
    private UserController testUserController;
    private UserDAO testUserDAO;

    @BeforeEach
    public void setUpCartController()
    {
        testProductDAO = mock(ProductDAO.class);
        testUserDAO = mock(UserDAO.class);
        testUserController = new UserController(testUserDAO);
        User user = new User("Tashi", 2);
        testLoginController = new LoginController(testUserController);
        testLoginController.currentUser = user;
        testShoppingCart = new ShoppingCart(testProductDAO, testUserDAO);
        testCartController = new CartController(testProductDAO,testLoginController,testShoppingCart);
    }

    @Test
    public void testAddItemToCart() throws IOException
    {
        Product test = new Product(100, "testing", 0, 0);
        when(testProductDAO.getProduct(100)).thenReturn(test);
        ArrayList<Product> cart = testLoginController.currentUser.getCart();
        int size = cart.size();
        testCartController.addItemToCart(100);
        int id = -1;
        // System.out.println("help " +  cart);
        for(Product product:cart)
        {
            if(product.getId()==100)
            {
                id = product.getId();
                break;
            }
        }
        assertEquals(100,id);
        assertEquals(size+1,testLoginController.currentUser.getCart().size());
    }
    public void testRemoveItemToCart() throws IOException
    {
        Product test = new Product(100, "testing", 0, 0);
        when(testProductDAO.getProduct(100)).thenReturn(test);
        testCartController.addItemToCart(100);
        ArrayList<Product> cart = testLoginController.currentUser.getCart();
        int size = cart.size();
        testCartController.removeItemFromCart(100);
        int id = -1;
        for(Product product:cart)
        {
            if(product.getId()==100)
            {
                id = product.getId();
                break;
            }
        }
        assertTrue(id!=100);
        assertEquals(size-1,testLoginController.currentUser.getCart().size());
    }
}
