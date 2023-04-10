package com.estore.api.estoreapi.persistence;
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
import java.util.ArrayList;
import java.util.List;

import com.estore.api.estoreapi.model.Discount;
import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class DiscountFileDAOTests {
    private ObjectMapper mockObjectMapper;
    private DiscountFileDAO testDiscountFileDAO;
    private Discount testDiscounts[];

    @BeforeEach
    public void setUp() throws IOException
    {
        mockObjectMapper = mock(ObjectMapper.class);
        testDiscounts = new Discount[2];
        testDiscounts[0] = new Discount(100,"10",10);
        testDiscounts[1] = new Discount(101,"20",20);

        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Discount[].class))
                .thenReturn(testDiscounts);
        testDiscountFileDAO = new DiscountFileDAO("doesnt_matter.txt",mockObjectMapper); 
    }
    
    @Test
    public void testGetDiscount()
    {
        Discount actual = testDiscountFileDAO.getDiscount("10");
        Discount actual2 = testDiscountFileDAO.getDiscount("20");

        assertEquals(testDiscounts[0],actual);
        assertEquals(testDiscounts[1],actual2);
    }
    @Test
    public void testGetDiscounts()
    {
        Discount actual[] = testDiscountFileDAO.getDiscounts();
        assertEquals(testDiscounts[0],actual[0]);
        assertEquals(testDiscounts[1],actual[1]);
    }
    @Test
    public void testCreateDiscount() throws IOException
    {
        // Discount newDiscount = new Discount(102,"30",30);
        // Discount newCode = testDiscountFileDAO.createDiscount(newDiscount);

        // assertEquals(newDiscount,newCode);

        Discount newDiscount = new Discount(102,"30",30);
        Discount result = assertDoesNotThrow(() -> testDiscountFileDAO.createDiscount(newDiscount),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Discount actual = testDiscountFileDAO.getDiscount(newDiscount.getName());
        assertEquals(actual.getId(),newDiscount.getId());
        assertEquals(actual.getName(),newDiscount.getName());
    }
    @Test
    public void testUpdateDiscount()
    {
        Discount newDiscount = new Discount(101,"20",50);

        // Invoke
        Discount result = assertDoesNotThrow(() -> testDiscountFileDAO.updateDiscount(newDiscount),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Discount actual = testDiscountFileDAO.getDiscount(newDiscount.getName());
        assertEquals(actual,newDiscount);
    }
    @Test
    public void testDeleteDiscount()
    {
        boolean result = assertDoesNotThrow(() -> testDiscountFileDAO.deleteDiscount("20"), 
          "Unexpected exception thrown");

          assertEquals(result,true);

          assertEquals(testDiscountFileDAO.codes.size(),testDiscounts.length-1);
    }
}

