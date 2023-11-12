/**
 * Represents a pizza object, with toppings and a size.
 */
package com.github.bishopl.pizzatime.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pizza {
    private PizzaSize pizzaSize;
    private List<PizzaTopping> toppings;

    /**
     * Constructs a new Pizza of a certain size and with a list of PizzaToppings.
     * 
     * @param pizzaSize the size of the pizza
     * @param toppings  the list of toppings on the pizza
     */
    @JsonCreator
    public Pizza(@JsonProperty("size") PizzaSize pizzaSize, @JsonProperty("toppings") List<PizzaTopping> toppings) {
        this.pizzaSize = pizzaSize;
        this.toppings = toppings;
    }

    /**
     * Constructs a new Pizza of a certain size with default topping.
     * 
     * @param pizzaSize the size of the pizza
     */
    public Pizza(PizzaSize pizzaSize) {
        this.pizzaSize = pizzaSize;
        ArrayList<PizzaTopping> defaultToppings = new ArrayList<>();
        defaultToppings.add(new PizzaTopping());
        this.toppings = defaultToppings;
    }

    /**
     * Constructs a default Pizza. Medium Size, Only Cheese.
     */
    public Pizza() {
        this(PizzaSize.MEDIUM);
    }

    /**
     * Sets the size of the pizza.
     * 
     * @param pizzaSize
     */
    public void setPizzaSize(PizzaSize pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    /**
     * Returns the size of the pizza.
     * 
     * @return the size of the pizza
     */
    public PizzaSize getPizzaSize() {
        return pizzaSize;
    }

    /**
     * Adds a topping to the pizza. If the topping already exists, updates its
     * amount.
     * 
     * @param topping the topping to add or update
     */
    public void addTopping(PizzaTopping topping) {
        int index = toppings.indexOf(topping);

        if (index != -1) {
            PizzaTopping existingTopping = toppings.get(index);
            existingTopping.setToppingAmount(topping.getToppingAmount());
        } else {
            toppings.add(topping);
        }
    }

    /**
     * Removes a topping from the pizza.
     * 
     * @param topping the topping to remove
     */
    public void removeTopping(PizzaTopping topping) {
        toppings.remove(topping);
    }

    /**
     * Returns the list of toppings on the pizza.
     * 
     * @return the list of toppings on the pizza
     */
    public List<PizzaTopping> getToppings() {
        return toppings;
    }

}
