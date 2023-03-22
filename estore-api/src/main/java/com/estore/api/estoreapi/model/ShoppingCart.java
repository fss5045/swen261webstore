package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.controller.ProductController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

    /**
     * Shopping cart functionality where the customer is able to modify the cart by checking out
     * adding an item, removing and item. And admins should be able to edit a specific items attributes.
     */
public class ShoppingCart {
    private User user;
    private ProductDAO productDao;

    public ShoppingCart(User user, ProductDAO productDAO){
        this.user = user;
        this.productDao = productDao;
    }
    /**
     * Adds a {@linkplain Product product} to the shopping cart.
     * 
     * @param id The id of the {@link Product product} in the shopping cart. 
     * 
     * @return None
     */
    public void addItem(int id) throws IOException{
        ArrayList<Product> cart  = user.getCart();
        cart.add(productDao.getProduct(id));
        user.setCart(cart);
    }
    /**
     * Removes a {@linkplain Product product} to the shopping cart.
     * 
     * @param id The id of the {@link Product product} in the shopping cart. 
     * 
     * @return None
     */
    public void removeItem(int id) throws IOException{
        ArrayList<Product> cart  = user.getCart();
        cart.remove(productDao.getProduct(id));
        user.setCart(cart);

    }
}
