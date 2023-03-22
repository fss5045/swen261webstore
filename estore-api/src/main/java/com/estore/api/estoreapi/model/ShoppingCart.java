package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.persistence.ProductDAO;

// shopping cart needs to remove, add, edit quantity, checkout, 
public class ShoppingCart {
    private User user;
    private ProductDAO productDao;

    public ShoppingCart(User user){
        this.user = user;
    }

    public void addItem(int id) throws IOException{
        ArrayList<Product> cart  = user.getCart();
        cart.add(productDao.getProduct(id));
        user.setCart(cart);
    }
    
    public void removeItem(int id) throws IOException{
        ArrayList<Product> cart  = user.getCart();
        cart.remove(productDao.getProduct(id));
        user.setCart(cart);

    }
}
