package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.UserDAO;

// shopping cart needs to remove, add, edit quantity, checkout, 
@Component
public class ShoppingCart {
    private UserDAO userDao;
    private ProductDAO productDao;

    public ShoppingCart(ProductDAO productDao, UserDAO userDao){
        this.productDao = productDao;
        this.userDao = userDao;
    }

    public void addItem(User user, int id) throws IOException{
        ArrayList<Product> cart  = user.getCart();
        cart.add(productDao.getProduct(id));
        user.setCart(cart);
        userDao.updateUser(user);
    }
    
    public void removeItem(User user, int id) throws IOException{
        ArrayList<Product> cart  = user.getCart();
        cart.remove(productDao.getProduct(id));
        user.setCart(cart);
        userDao.updateUser(user);
    }
}
