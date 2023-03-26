package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDAO;

/**
 * Test the User Controller class
 * 
 * @author Finn Saunders-Zurn fss5045@rit.edu
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDao;

    /**
     * Before each test, create a new UserController object and inject
     * a mock UserDAO
     */
    @BeforeEach
    public void setupUserController() {
        mockUserDao = mock(UserDAO.class);
        userController = new UserController(mockUserDao);
    }

    @Test
    public void testGetUser() throws IOException {  // getUser may throw IOException
        // Setup
        User user = new User("test", 1);
        // When the same id is passed in, our mock User DAO will return the User object
        when(mockUserDao.getUser(user.getUsername())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getUsername());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws Exception { // createUser may throw IOException
        // Setup
        String username = "testingnull";
        // When the same username is passed in, our mock User DAO will return null, simulating
        // no user found
        when(mockUserDao.getUser(username)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(username);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws Exception { // createUser may throw IOException
        // Setup
        String username = "testingnull";
        // When getUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDao).getUser(username);

        // Invoke
        ResponseEntity<User> response = userController.getUser(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateUser() throws IOException {  // createUser may throw IOException
        // Setup
        User user = new User("test2", 1);
        // when createUser is called, return true simulating successful
        // creation and save
        when(mockUserDao.createUser(user.getUsername())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user.getUsername());

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException {  // createUser may throw IOException
        // Setup
        User user = new User("test2null", 1);
        // when createUser is called, return false simulating failed
        // creation and save
        when(mockUserDao.createUser(user.getUsername())).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user.getUsername());

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateUserHandleException() throws IOException {  // createUser may throw IOException
        // Setup
        User user = new User("test2fail", 1);
        // When createUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDao).createUser(user.getUsername());

        // Invoke
        ResponseEntity<User> response = userController.createUser(user.getUsername());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetUsers() throws IOException { // getUsers may throw IOException
        // Setup
        User[] users = new User[2];
        users[0] = new User("test", 1);
        users[1] = new User("test2", 2);
        // When getUsers is called return the Users created above
        when(mockUserDao.getUsers()).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    @Test
    public void testGetUsersHandleException() throws IOException { // getUsers may throw IOException
        // Setup
        // When getUsers is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDao).getUsers();

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException { // deleteUser may throw IOException
        // Setup
        String username = "test";
        // when deleteUser is called return true, simulating successful deletion
        when(mockUserDao.deleteUser(username)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(username);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException { // deleteUser may throw IOException
        // Setup
        String username = "test";
        // when deleteUser is called return false, simulating failed deletion
        when(mockUserDao.deleteUser(username)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(username);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteUserHandleException() throws IOException { // deleteUser may throw IOException
        // Setup
        String username = "test";
        // When deleteUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDao).deleteUser(username);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
