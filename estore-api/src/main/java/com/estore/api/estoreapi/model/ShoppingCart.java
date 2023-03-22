package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.persistence.ProductDAO;

// shopping cart needs to remove, add, edit quantity, checkout, 
@Component
public class ShoppingCart {
    // private User user;
    private ProductDAO productDao;

    public ShoppingCart(ProductDAO productDao){
        this.productDao = productDao;
    }

    public void addItem(User user, int id) throws IOException{
        ArrayList<Product> cart  = user.getCart();
        cart.add(productDao.getProduct(id));
        user.setCart(cart);
    }
    
    public void removeItem(User user, int id) throws IOException{
        ArrayList<Product> cart  = user.getCart();
        cart.remove(productDao.getProduct(id));
        user.setCart(cart);
    }
}
