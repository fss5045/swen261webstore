package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Hero class
 * 
 * @author Finn Saunders-Zurn fss5045@rit.edu
 */
@Tag("Model-tier")
public class productTests {
    public int id = 9999;
    public String name = "generic";
    public int number = 3;
    public int price = 10;
    
    
    @Test
    public void testCtor(){
        Product actual = new Product(id, name, number, price);
        assertTrue(actual instanceof Product);
    }

    @Test
    public void testGetId(){
        Product actual = new Product(id, name, number, price);
        int id = 9999;
        assertEquals(id, actual.getId());
    }

    @Test
    public void testName(){
        Product actual = new Product(id, name, number, price);
        String name = "generic";
        assertEquals(name, actual.getName());
        String new_name = "new";
        actual.setName(new_name);
        assertEquals(new_name, actual.getName());

    }

    @Test
    public void testSport(){
        Product actual = new Product(id, name, number, price);
        String sport = "sport";
        actual.setSport(sport);
        assertEquals(sport, actual.getSport());
    }

    @Test
    public void testColor(){
        Product actual = new Product(id, name, number, price);
        String color = "color";
        actual.setColor(color);
        assertEquals(color, actual.getColor());
    }

    @Test
    public void testNumber(){
        Product actual = new Product(id, name, number, price);
        int number = 3;
        assertEquals(number, actual.getNumber());
        int new_number = actual.getNumber() - 1;
        actual.setNumber(new_number);
        assertEquals(new_number, actual.getNumber());
    }

    @Test
    public void testPrice(){
        Product actual = new Product(id, name, number, price);
        int price = 10;
        assertEquals(price, actual.getPrice());
        int new_price = actual.getPrice() - 1;
        actual.setPrice(new_price);
        assertEquals(new_price, actual.getPrice());
    }

    @Test
    public void testToString(){
        Product actual = new Product(id, name, number, price);
        int id = 9999;
        String name = "generic";
        int number = 3;
        int price = 10;
        String sport = "sport";
        String color = "color";

        actual.setSport(sport);
        actual.setColor(color);

        String expectedString = String.format(Product.STRING_FORMAT, id, name, number, price, sport, color);

        assertEquals(expectedString, actual.toString());
    }
}
