package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Discount;

@Component
public class DiscountFileDAO implements DiscountDAO{
    Map<String,Discount> codes;
    private static int nextId;
    private ObjectMapper objectMapper;
    private String filename;
    private static final Logger LOG = Logger.getLogger(DiscountFileDAO.class.getName());

    public DiscountFileDAO(@Value("${codes.file}") String filename, ObjectMapper objectMapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates an array of {@linkplain Discount discount} from the tree map for any
     * {@linkplain Discount discount} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Discount discount}
     * in the tree map
     * 
     * @return  The array of {@link Discount discount}, may be empty
     */
    private Discount[] getCodesArray() { // if containsText == null, no filter
        ArrayList<Discount> discountArrayList = new ArrayList<>();
        for (Discount code : codes.values()) {
            discountArrayList.add(code);
        }
        Discount[] discountArray = new Discount[discountArrayList.size()];
        discountArrayList.toArray(discountArray);
        return discountArray;
    }

    /**
     * Loads {@linkplain Discount discount} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        codes = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of discount
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Discount[] codeArray = objectMapper.readValue(new File(filename),Discount[].class);

        // Add each discount to the tree map and keep track of the greatest id
        for (Discount code : codeArray) {
            codes.put(code.getName(), code);
            if (code.getId() > nextId)
                nextId = code.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
     * Saves the {@linkplain Discount discount} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Discount discount} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Discount[] codeArray = getCodesArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),codeArray);
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Discount getDiscount(String name) {
        synchronized(codes) {
            if (codes.containsKey(name))
                return codes.get(name);
            else
                return null;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Discount[] getDiscounts() {
        synchronized(codes) {
            return getCodesArray();
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Discount createDiscount(Discount discount) throws IOException{
        synchronized(codes) {
            // We create a new discount object because the id field is immutable
            // and we need to assign the next unique id
            Discount newCode = new Discount(nextId, discount.getName(), discount.getAmount());
            codes.put(newCode.getName(),newCode);
            save(); // may throw an IOException
            return newCode;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Discount updateDiscount(Discount discount) throws IOException{
        synchronized(codes) {
            if (codes.containsKey(discount.getName()) == false)
                return null;
            
            //update discounts
            LOG.info("updating discount");
            codes.put(discount.getName(), discount);
            save();
            // LOG.info(discount.getCart().toString());
            return discount;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public boolean deleteDiscount(String name) throws IOException{
        synchronized(codes) {
            if (codes.containsKey(name)) {
                codes.remove(name);
                return save();
            }
            else
                return false;
        }
    }
    
}
