package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
// import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;

@Component
public class ProductFileDAO implements ProductDAO {
    // private static final Logger LOG = Logger.getLogger(ProductFileDAO.class.getName());
    Map<Integer,Product> products;   // Provides a local cache of the product objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between product
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new product
    private String filename;    // Filename to read from and write to

    /**
     * Creates a product File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ProductFileDAO(@Value("${products.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the product from the file
    }
    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Product product} from the tree map
     * 
     * @return  The array of {@link Product product}, may be empty
     */
    private Product[] getProductsArray() {
        return getProductsArray(null);
    }

    /**
     * Generates an array of {@linkplain Product product} from the tree map for any
     * {@linkplain Product product} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Product product}
     * in the tree map
     * 
     * @return  The array of {@link Product product}, may be empty
     */
    private Product[] getProductsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

        /**
     * Saves the {@linkplain Product product} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Product product} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),productArray);
        return true;
    }

        /**
     * Loads {@linkplain Product product} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        products = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of product
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Product[] productArray = objectMapper.readValue(new File(filename),Product[].class);

        // Add each product to the tree map and keep track of the greatest id
        for (Product product : productArray) {
            products.put(product.getId(),product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product[] getProducts() {
        synchronized(products) {
            return getProductsArray();
        }
    }
    
        /**
    ** {@inheritDoc}
     */
    @Override
    public Product[] findProducts(String containsText) {
        synchronized(products) {
            return getProductsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product getProduct(int id) {
        synchronized(products) {
            if (products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            // We create a new product object because the id field is immutable
            // and we need to assign the next unique id
            Product newProduct = new Product(nextId(), product.getName(), product.getNumber(), product.getPrice());
            products.put(newProduct.getId(),newProduct);
            save(); // may throw an IOException
            return newProduct;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(products) {
            if (products.containsKey(product.getId()) == false)
                return null;  // product does not exist

            products.put(product.getId(),product);
            save(); // may throw an IOException
            return product;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized(products) {
            if (products.containsKey(id)) {
                products.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
