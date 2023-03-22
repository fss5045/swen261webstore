package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.persistence.ProductDAO;

    /**
     * Shopping cart functionality where the customer is able to modify the cart by checking out
     * adding an item, removing and item. And admins should be able to edit a specific items attributes.
     */
public class ShoppingCart {
    private User user;
    private ProductDAO productDao;

    public ShoppingCart(User user){
        this.user = user;
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
