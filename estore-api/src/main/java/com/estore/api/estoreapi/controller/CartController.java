package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.model.Enums.UserType;
import com.estore.api.estoreapi.persistence.ProductDAO;

@RestController
@RequestMapping("/cart")
public class CartController{
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private ProductDAO productDao;
    private LoginController loginController;
    private ShoppingCart shoppingCart;


    public CartController(ProductDAO productDao, LoginController loginController, ShoppingCart shoppingCart){
        this.productDao = productDao;
        this.loginController = loginController;
        this.shoppingCart = shoppingCart;
    }
 
    @PutMapping("")
    public ResponseEntity<User> addItemToCart(@RequestBody int id){
        LOG.info("PUT /cart " + id);
        if(loginController.currentUser.getUserType() != UserType.Customer){
            LOG.info("NOT LOGGED INTO AS A CUSTOMER");
            return new ResponseEntity<>(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }
        try {
            if (productDao.getProduct(id) != null){
                shoppingCart.addItem(loginController.currentUser, id);
                return new ResponseEntity<User>(loginController.currentUser,HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<User> removeItemFromCart(@RequestBody int id){
        LOG.info("DELETE /cart " + id);
        if(loginController.currentUser.getUserType() != UserType.Customer){
            LOG.info("NOT LOGGED INTO AS A CUSTOMER");
            return new ResponseEntity<>(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }
        try {
            if (productDao.getProduct(id) != null){
                shoppingCart.removeItem(loginController.currentUser, id);
                return new ResponseEntity<User>(loginController.currentUser,HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
