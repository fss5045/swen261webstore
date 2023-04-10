package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Discount {
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("amount") private int amount;

    @JsonCreator
    public Discount(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("amount") int amount){
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    /**
     * Retrieves the id of the discount
     * @return The id of the discount
     */
    public int getId() {return id;}

    /**
     * Sets the name of the discount
     * @param name The name of the discount
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the discount
     * @return The name of the discount
     */
    public String getName() {return name;}

    /**
     * Sets the amount of the discount
     * @param amount The amount of the discount
     */
    public void setAmount(int amount) {this.amount = amount;}

    /**
     * Retrieves the amount of the discount
     * @return The amount of the discount
     */
    public int getAmount() {return amount;}
}
