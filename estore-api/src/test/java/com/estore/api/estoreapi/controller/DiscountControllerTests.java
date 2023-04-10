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

import com.estore.api.estoreapi.model.Discount;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.DiscountDAO;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class DiscountControllerTests {
    private DiscountDAO mockDiscountDAO;
    private DiscountController mockDiscountController;
    private LoginController mockLoginController;

    @BeforeEach
    public void setupDiscountController()
    {
        mockDiscountDAO = mock(DiscountDAO.class);
        mockLoginController = mock(LoginController.class);
        User admin = new User("admin", 0);
        mockDiscountController = new DiscountController(mockDiscountDAO, mockLoginController);
        mockDiscountController.loginController.currentUser = admin;
    }
    @Test
    public void testGetDiscount() throws IOException
    {
        Discount newDiscount = new Discount(100,"20%", 20);

        //when(testDiscountDAO.getDiscount(newDiscount.getName())).thenReturn(newDiscount);
        when(mockDiscountDAO.getDiscount(newDiscount.getName())).thenReturn(newDiscount);

        // Invoke
        ResponseEntity<Discount> response = mockDiscountController.getDiscount(newDiscount.getName());
        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(newDiscount,response.getBody());
    }
    @Test
    public void testGetDiscountNotFound() throws Exception { // createProduct may throw IOException
        // Setup
        String name = "100";
        // When the same id is passed in, our mock Product DAO will return null, simulating
        // no product found
        when(mockDiscountDAO.getDiscount(name)).thenReturn(null);

        // Invoke
        ResponseEntity<Discount> response = mockDiscountController.getDiscount(name);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
    @Test
    public void testGetDiscountHandleException() throws Exception {
        // Setup
        String name = "100";
        // When getProduct is called on the Mock Product DAO, throw an IOException
        when(mockDiscountDAO.getDiscount(name)).thenThrow(new IOException());
    
        // Invoke
        ResponseEntity<Discount> response = mockDiscountController.getDiscount(name);
    
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
 
    @Test
    public void testCreateDiscount() throws IOException {  // createProduct may throw IOException
        // Setup
        Discount newDiscount = new Discount(100,"30",30);
        // when createProduct is called, return true simulating successful
        // creation and save
        when(mockDiscountDAO.createDiscount(newDiscount)).thenReturn(newDiscount);

        // Invoke
        // System.out.println("help " + productController.loginController.currentUser.getUserType());
        ResponseEntity<Discount> response = mockDiscountController.createDiscount(newDiscount);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(newDiscount,response.getBody());
    }

    @Test
    public void testCreateDiscountFailed() throws IOException {  // createProduct may throw IOException
        // Setup
        Discount newDiscount = new Discount(1, "new one", 10);
        // when createProduct is called, return false simulating failed
        // creation and save
        when(mockDiscountDAO.getDiscount(newDiscount.getName())).thenReturn(newDiscount);

        // Invoke
        ResponseEntity<Discount> response = mockDiscountController.createDiscount(newDiscount);

        // // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateDiscountHandleException() throws IOException {  // createProduct may throw IOException
        // Setup
        Discount newDiscount = new Discount(1, "new one", 10);
        // When createProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDiscountDAO).createDiscount(newDiscount);

        // Invoke
        ResponseEntity<Discount> response = mockDiscountController.createDiscount(newDiscount);

        // // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    @Test
    public void testUpdateDiscount() throws IOException { // updateProduct may throw IOException
        // Setup
        Discount newDiscount = new Discount(1, "new one", 10);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockDiscountDAO.updateDiscount(newDiscount)).thenReturn(newDiscount);
        ResponseEntity<Discount> response = mockDiscountController.updateDiscount(newDiscount);
        newDiscount.setName("new one");

        // Invoke
        response = mockDiscountController.updateDiscount(newDiscount);

        // // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(newDiscount,response.getBody());
    }

    @Test
    public void testUpdateDiscountFailed() throws IOException { // updateProduct may throw IOException
        // Setup
        Discount newDiscount = new Discount(1, "new one", 10);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockDiscountDAO.updateDiscount(newDiscount)).thenReturn(null);

        // Invoke
        ResponseEntity<Discount> response = mockDiscountController.updateDiscount(newDiscount);

        // // Analyze/
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateDiscountHandleException() throws IOException { // updateProduct may throw IOException
        // Setup
        Discount newDiscount = new Discount(1, "new one", 10);
        // When updateProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDiscountDAO).updateDiscount(newDiscount);

        // // Invoke
        ResponseEntity<Discount> response = mockDiscountController.updateDiscount(newDiscount);

        // // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    @Test
    public void testGetDiscounts() throws IOException { // getProducts may throw IOException
        // Setup
        Discount testDiscounts[] = new Discount[2];
        testDiscounts[0] = new Discount(100,"10",10);
        testDiscounts[1] = new Discount(101,"20",20);
        // When getProducts is called return the products created above
        when(mockDiscountDAO.getDiscounts()).thenReturn(testDiscounts);

        // Invoke
        ResponseEntity<Discount[]> response = mockDiscountController.getDiscounts();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testDiscounts,response.getBody());
    }

    @Test
    public void testGetDiscountsHandleException() throws IOException { // getProducts may throw IOException
        // Setup
        // When getProducts is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDiscountDAO).getDiscounts();

        // Invoke
        ResponseEntity<Discount[]> response = mockDiscountController.getDiscounts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    @Test
    public void testDeleteDiscount() throws IOException { // deleteProduct may throw IOException
        // Setup
        String name = "nothing";
        // when deleteProduct is called return true, simulating successful deletion
        when(mockDiscountDAO.deleteDiscount(name)).thenReturn(true);

        // Invoke
        ResponseEntity<Discount> response = mockDiscountController.deleteDiscount(name);

        // // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteDiscountNotFound() throws IOException { // deleteProduct may throw IOException
        // Setup
        String name = "nothing";
        // when deleteProduct is called return false, simulating failed deletion
        when(mockDiscountDAO.deleteDiscount(name)).thenReturn(false);

        // Invoke
        ResponseEntity<Discount> response = mockDiscountController.deleteDiscount(name);

        // // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteDiscountHandleException() throws IOException { // deleteProduct may throw IOException
        // Setup
        String name = "nothing";
        // When deleteProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockDiscountDAO).deleteDiscount(name);

        // Invoke
        ResponseEntity<Discount> response = mockDiscountController.deleteDiscount(name);

        // // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
