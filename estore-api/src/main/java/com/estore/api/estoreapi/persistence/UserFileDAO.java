package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.User;

@Component
public class UserFileDAO implements UserDAO{
    Map<String,User> users;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());

    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    // private synchronized static int nextId() {
    //     int id = nextId;
    //     ++nextId;
    //     return id;
    // }

    /**
     * Generates an array of {@linkplain User user} from the tree map for any
     * {@linkplain User user} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain User user}
     * in the tree map
     * 
     * @return  The array of {@link Product product}, may be empty
     */
    private User[] getUsersArray() { // if containsText == null, no filter
        ArrayList<User> userArrayList = new ArrayList<>();
        for (User user : users.values()) {
            userArrayList.add(user);
        }
        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

        /**
     * Saves the {@linkplain Product product} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Product product} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

        /**
     * Loads {@linkplain Product product} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of product
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each product to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getUsername(), user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User[] getUsers(){
        synchronized(users) {
            return getUsersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User getUser(String username){
        synchronized(users) {
            if (users.containsKey(username))
                return users.get(username);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User createUser(String username) throws IOException{
        synchronized(users) {
            // We create a new product object because the id field is immutable
            // and we need to assign the next unique id
            User newUser = new User(username, nextId);
            users.put(newUser.getUsername(),newUser);
            save(); // may throw an IOException
            return newUser;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteUser(String username) throws IOException{
        synchronized(users) {
            if (users.containsKey(username)) {
                users.remove(username);
                return save();
            }
            else
                return false;
        }
    }

    /**
    ** {@inheritDoc}}
     */
    @Override
    public User updateUser(User user) throws IOException{
        synchronized(users) {
            if (users.containsKey(user.getUsername()) == false)
                return null;
            
            //update users
            LOG.info("updating user");
            users.put(user.getUsername(), user);
            save();
            // LOG.info(user.getCart().toString());
            return user;
        }
    }
    
}
