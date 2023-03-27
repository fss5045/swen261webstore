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
    private LoginController testLogin;
    private UserController testUser;
    private UserDAO testUserDAO;
    
    @BeforeEach
    public void setUpLoginController()
    {
        testUserDAO = mock(UserDAO.class);
        testUser = new UserController(testUserDAO);
        testLogin = new LoginController(testUser);
    }
    @Test
    public void testLogin() throws IOException
    {
        //User user1 = testUserDAO.createUser("Tashi");
        User user2 = testUserDAO.createUser("Admin");
        
        //testLogin.login("Tashi");

        //assertEquals(testLogin.currentUser,user1);

        testLogin.login("Admin");

        assertEquals(testLogin.currentUser,user2);

    }
    @Test
    public void testLogout()
    {
        testLogin.logout();

        assertNull(testLogin.currentUser);
    }
}
