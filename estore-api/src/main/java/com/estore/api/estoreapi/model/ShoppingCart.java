package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.persistence.ProductDAO;

<<<<<<< HEAD
// shopping cart needs to remove, add, edit quantity, checkout, 
@Component
=======
    /**
     * Shopping cart functionality where the customer is able to modify the cart by checking out
     * adding an item, removing and item. And admins should be able to edit a specific items attributes.
     */
>>>>>>> ece4f093e43bca0b65a6c57aa0950e141730d2c6
public class ShoppingCart {
    // private User user;
    private ProductDAO productDao;

    public ShoppingCart(ProductDAO productDao){
        this.productDao = productDao;
    }
<<<<<<< HEAD

    public void addItem(User user, int id) throws IOException{
=======
    /**
     * Adds a {@linkplain Product product} to the shopping cart.
     * 
     * @param id The id of the {@link Product product} in the shopping cart. 
     * 
     * @return None
     */
    public void addItem(int id) throws IOException{
>>>>>>> ece4f093e43bca0b65a6c57aa0950e141730d2c6
        ArrayList<Product> cart  = user.getCart();
        cart.add(productDao.getProduct(id));
        user.setCart(cart);
    }
<<<<<<< HEAD
    
    public void removeItem(User user, int id) throws IOException{
=======
    /**
     * Removes a {@linkplain Product product} to the shopping cart.
     * 
     * @param id The id of the {@link Product product} in the shopping cart. 
     * 
     * @return None
     */
    public void removeItem(int id) throws IOException{
>>>>>>> ece4f093e43bca0b65a6c57aa0950e141730d2c6
        ArrayList<Product> cart  = user.getCart();
        cart.remove(productDao.getProduct(id));
        user.setCart(cart);
    }
}
