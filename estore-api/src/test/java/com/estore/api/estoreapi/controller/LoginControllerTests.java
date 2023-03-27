package com.estore.api.estoreapi.controller;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class LoginControllerTests {
    private UserController testUserController;
    private UserDAO testUserDAO;
    private LoginController testLoginController;
    
    @BeforeEach
    public void setUpLoginController()
    {
        testUserDAO = mock(UserDAO.class);
        testUserController = new UserController(testUserDAO);
        testLoginController = new LoginController(testUserController);
    }
    @Test
    public void testLogin() throws IOException
    {
        User user1 = new User("Tashi", 2);
        User user2 = new User("admin", 0);
        
        when(testUserDAO.getUser(user1.getUsername())).thenReturn(user1);
        testLoginController.login("Tashi");

        assertEquals(testLoginController.currentUser,user1);

        when(testUserDAO.getUser(user2.getUsername())).thenReturn(user2);
        testLoginController.login("admin");

        assertEquals(testLoginController.currentUser,user2);

    }
    @Test
    public void testLogout()
    {
        testLoginController.logout();

        assertNull(testLoginController.currentUser);
    }
}
