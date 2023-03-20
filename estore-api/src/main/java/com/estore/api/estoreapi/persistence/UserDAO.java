package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;

public interface UserDAO {
    
    /**
     * Retrieves all {@linkplain User users}
     * 
     * @return An array of {@link User user} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * Retrieves a {@linkplain User user} with the given id
     * 
     * @param id The id of the {@link User user} to get
     * 
     * @return a {@link User user} object with the matching id
     * <br>
     * null if no {@link User user} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(String username) throws IOException;

    /**
     * Creates and saves a {@linkplain User user}
     * 
     * @param username username for object to be created and saved
     * <br> 
     * @return new {@link User user} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(String username) throws IOException;

    /**
     * Deletes a {@linkplain User user} with the given id
     * 
     * @param id The username of the {@link User user}
     * 
     * @return true if the {@link User user} was deleted
     * <br>
     * false if user with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(String username) throws IOException;

}
