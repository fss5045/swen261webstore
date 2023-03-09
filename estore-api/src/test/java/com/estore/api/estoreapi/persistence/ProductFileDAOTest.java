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

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Persistence-Tier")
public class ProductFileDAOTest{
    ObjectMapper mockObjectMapper;
    ProductFileDAO productFileDAO;
    Product[] testProducts;

    @BeforeEach
    public void setUpHeroFileDAO() throws IOException
    {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(99,"basketball",100,20);
        testProducts[1] = new Product(100,"soccerball",100,10);
        testProducts[2] = new Product(101,"volleyball",100,15);
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Product[].class))
                .thenReturn(testProducts);
        productFileDAO = new ProductFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetProducts()
    {
        Product[] products = productFileDAO.getProducts();

        assertEquals(products.length , testProducts.length);
        for(int i = 0;i <testProducts.length;i++)
            assertEquals(products[i],testProducts[i]);
    }

    @Test
    public void testFindProducts()
    {
        Product[] products = productFileDAO.findProducts("ball");

        assertEquals(products.length,3);
        assertEquals(products[0],testProducts[0]);
        assertEquals(products[1],testProducts[1]);
        assertEquals(products[2],testProducts[2]);
    }

    @Test
    public void testGetProduct()
    {
        Product product = productFileDAO.getProduct(99);

        assertEquals(product,testProducts[0]);
    }

    @Test
    public void testDeleteProduct() 
    {
          boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(99), 
          "Unexpected exception thrown");

          assertEquals(result,true);

          assertEquals(productFileDAO.products.size(),testProducts.length-1);
    }

    @Test
    public void testCreateHero()
    {
        Product product = new Product(102,"baseball",100,5);

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.createProduct(product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = productFileDAO.getProduct(product.getId());
        assertEquals(actual.getId(),product.getId());
        assertEquals(actual.getName(),product.getName());
    }

    @Test
    public void testUpdateProduct()
    {
        Product product = new Product(99,"tennisball", 100,5);

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = productFileDAO.getProduct(product.getId());
        assertEquals(actual,product);
    }

    @Test
    public void testSaveException() throws IOException
    {
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Product[].class));

        Product product = new Product(102,"baseball",100,5);

        assertThrows(IOException.class,
                        () -> productFileDAO.createProduct(product),
                        "IOException not thrown");
    }

    @Test
    public void testGetProductNotFound()
    {
        Product product = productFileDAO.getProduct(98);

        assertEquals(product,null);
    }

    @Test
    public void testDeleteHeroNotFound()
    {
        boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(98),
                                                "Unexpected exception thrown");
        assertEquals(result,false);
        assertEquals(productFileDAO.products.size(),testProducts.length);
    }

    @Test
    public void testUpdateProductNotFound()
    {
        Product product = new Product(98,"pingpongball",100,5);
        
        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product)
                                                    ,"Unexpected exception thrown");
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException
    {
        mockObjectMapper = mock(ObjectMapper.class);
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new ProductFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
} 
