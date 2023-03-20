package com.estore.api.estoreapi.model;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.ProductDAO;
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

    public void addItem(int id){
        user.cart.add(productDao.getProduct(id));
    }
    
    public void removeItem(int id){
        user.cart.remove(productDao.getProduct(id));
    }
}
