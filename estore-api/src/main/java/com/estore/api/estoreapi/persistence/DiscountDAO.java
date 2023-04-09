package com.estore.api.estoreapi.persistence;


import java.io.IOException;
import com.estore.api.estoreapi.model.Discount;

public interface DiscountDAO {

    /**
     * Retrieves a {@linkplain Discount discount} with the given name
     * 
     * @param name The name of the {@link Discount discount} to get
     * 
     * @return a {@link Discount producy} object with the matching name
     * <br>
     * null if no {@link Discount discount} with a matching name is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Discount getDiscount(String name) throws IOException;

    /**
     * Retrieves all {@linkplain Discount discounts}
     * 
     * @return An array of {@link Discount discount} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Discount[] getDiscounts() throws IOException;

    /**
     * Creates and saves a {@linkplain Discount discount}
     * 
     * @param discount {@linkplain Discount discount} object to be created and saved
     * <br>
     * The name of the discount object is ignored and a new uniqe name is assigned
     *
     * @return new {@link Discount discount} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Discount createDiscount(Discount discount) throws IOException;

    /**
     * Updates and saves a {@linkplain Discount discount}
     * 
     * @param {@link Discount discount} object to be updated and saved
     * 
     * @return updated {@link Discount discount} if successful, null if
     * {@link Prodcut prodcut} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Discount updateDiscount(Discount discount) throws IOException;

    /**
     * Deletes a {@linkplain Discount discount} with the given name
     * 
     * @param name The name of the {@link Discount discount}
     * 
     * @return true if the {@link Discount discount} was deleted
     * <br>
     * false if discount with the given name does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteDiscount(String name) throws IOException;
}
