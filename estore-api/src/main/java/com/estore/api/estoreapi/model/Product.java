package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    @JsonProperty("price") private int price;

    // @JsonCreator
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("number") int number, @JsonProperty("price") int price){
        this.id = id;
        this.name = name;
        this.sport = "";
        this.color = "";
        this.number = number;
        this.price = price;
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
     * Retrieves the sport of the product
     * @return The sport of the product
     */
    public String getSport() {return sport;}


    /**
     * Sets the color of the product
     * @param color The color of the product
     */
    public void setColor(String color) {this.color = color;}

    /**
     * Retrieves the color of the product
     * @return The color of the product
     */
    public String getColor() {return color;}

    /**
     * Sets the number of the product
     * @param number The number of the product
     */
    public void setNumber(int number) {this.number = number;}

    /**
     * Retrieves the number of the product
     * @return The number of the product
     */
    public int getNumber() {return number;}

    /**
     * incriments the number
     * @param add the amount to increment the number by
     */
    public void addNumber(int add) {this.number += add;}

    /**
     * Sets the price of the product
     * @param price The number of the product
     */
    public void setPrice(int price) {this.price = price;}

    /**
     * Retrieves the price of the product
     * @return The price of the product
     */
    public int getPrice() {return price;}

    /**
     * incriments the price
     * @param add the amount to increment the price by
     */
    public void addPrice(int add) {this.price += add;}
}
