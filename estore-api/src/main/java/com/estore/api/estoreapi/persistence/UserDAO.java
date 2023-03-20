package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;

public interface UserDAO {
    
    User[] getUsers() throws IOException;

    User getUser(String username) throws IOException;

    User createUser(String username) throws IOException;

    boolean deleteUser(int id) throws IOException;

}
