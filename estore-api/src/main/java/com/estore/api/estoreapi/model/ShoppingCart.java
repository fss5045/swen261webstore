package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.controller.ProductController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// shopping cart needs to remove, add, edit quantity, checkout, 
public class ShoppingCart {
    private User user;
    private ProductDAO productDao;

    public ShoppingCart(User user, ProductDAO productDAO){
        this.user = user;
        this.productDao = productDao;
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
