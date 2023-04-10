package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag ("Model-Tier")
public class DiscountTests {
    int id = 100;
    String name = "random";
    int amount = 50;
    Discount discount;
    @BeforeEach
    public void setUp()
    {
        discount = new Discount(id,name,amount);
        assertTrue(discount instanceof Discount);
    }
    
    @Test
    public void testGetId()
    {
        int actual = discount.getId();
        assertEquals(id,actual);
    }
    
    @Test
    public void testGetName()
    {
        String actual = discount.getName();
        assertEquals(name,actual);
    }

    @Test
    public void testSetName()
    {
        discount.setName("HALF OFF");
        assertEquals("HALF OFF", discount.getName());
    }

    @Test
    public void testGetAmount()
    {
        int actual = discount.getAmount();
        assertEquals(50, actual);
    }
    @Test
    public void testSetAmount()
    {
        discount.setAmount(20);
        assertEquals(20,discount.getAmount());
    }
}
