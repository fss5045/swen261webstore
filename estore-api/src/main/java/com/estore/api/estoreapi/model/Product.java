package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a product 
 * 
 * @author fss5045@rit.edu
 */
public class Product{
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("sport") private String sport;
    @JsonProperty("color") private String color;
    @JsonProperty("number") private int number;

    /**
     * Create a product with the given properties
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("sport") String sport, 
    @JsonProperty("color") String color, @JsonProperty("number") int number){
        this.id = id;
        this.name = name;
        this.sport = sport;
        this.color = color;
        this.number = number;
    }

    /**
     * Retrieves the id of the product
     * @return The id of the product
     */
    public int getId() {return id;}

    /**
     * Sets the name of the product
     * @param name The name of the product
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getName() {return name;}

    /**
     * Sets the sport of the product
     * @param sport The sport of the product
     */
    public void setSport(String sport) {this.sport = sport;}

    /**
     * Retrieves the color of the product
     * @return The color of the product
     */
    public String getColor() {return color;}

    /**
     * Sets the color of the product
     * @param color The color of the product
     */
    public void setColor(String color) {this.color = color;}

    /**
     * Retrieves the number of the product
     * @return The number of the product
     */
    public int getNumber() {return number;}

    /**
     * Sets the number of the product
     * @param number The number of the product
     */
    public void setNumber(int number) {this.number = number;}

    /**
     * incriments the number
     * @param add the amount to increment the number by
     */
    public void addNumber(int add) {this.number += add;}
}
