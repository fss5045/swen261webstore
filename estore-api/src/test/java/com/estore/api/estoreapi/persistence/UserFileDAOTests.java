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

import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Persistence-Tier")
public class UserFileDAOTests {
    UserFileDAO userFileDAO;
    User[] testUsers;
    ObjectMapper mockObjectMapper;
    
    @BeforeEach
    public void setUpUserFileDAO() throws IOException
    {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[4];
        testUsers[0] = new User("Justin",100);
        testUsers[1] = new User("Paul",101);
        testUsers[2] = new User("Finn",102);
        testUsers[3] = new User("Tashi", 103);

        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),User[].class))
                .thenReturn(testUsers);
        userFileDAO = new UserFileDAO("doesnt_matter.txt",mockObjectMapper); 
    }

    @Test
    public void testGetUsers()
    {
        //problem is that actual is sorted in alphabetical order
        //while testusers does not always have to be
        User[] actual = userFileDAO.getUsers();
        List<Object> testUsersList = Arrays.asList(testUsers);
        assertEquals(testUsers.length , actual.length);
        for(int i = 0;i <testUsers.length;i++){
            //System.out.println(actual[i].getUsername());
            //System.out.println(testUsers[i].getUsername());
            //[finn, justin, paul, tashi] vs [justin, paul, finn, tashi]
            assertEquals(true, testUsersList.contains(actual[i]));
        }
    }

    @Test
    public void testGetUser()
    {
        User users = userFileDAO.getUser("Tashi");

        assertEquals(users,testUsers[3]);
    }
    
    @Test
    public void testCreateUser()
    {
        User user = new User("Jimmy", 104);

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.createUser("Jimmy"),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUser(user.getUsername());
        assertEquals(actual.getId(),user.getId());
        assertEquals(actual.getUsername(),user.getUsername());
    }

    @Test
    public void testDeleteUser()
    {
        boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser("Tashi"),
                                                "Unexpected exception thrown");
        assertEquals(true, result);
        assertEquals(userFileDAO.users.size(),testUsers.length-1);
    }
}

