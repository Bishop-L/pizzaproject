/**
 * Represents a pizza order, which contains a list of pizzas and the current time, and after checkout, the id and total cost will be added to order
 */
package com.github.bishopl.pizzatime.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class PizzaOrder {
    private long id;
    private Date date;
    private List<Pizza> pizzas;
    private double totalCost;

    /**
     * Constructs a new PizzaOrder with an empty list of pizzas and a date of the current time.
     */
    public PizzaOrder() {
        this.id = -1; // -1 means order has not been placed
        this.date = new Date();
        this.pizzas = new ArrayList<>();
        this.totalCost = 0.0;
    }

    /**
     * Constructs a new PizzaOrder with a list of pizzas added and a date of the current time.
     */
    public PizzaOrder(List<Pizza> pizzas){
        this();
        this.pizzas = pizzas;
    }
    
    /**
     * Returns the date of the pizza order.
     * @return the date of the pizza order
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Returns the total cost of the pizza order.
     * @return the total cost of the pizza order
     */
    public double getTotalCost() {
        return this.totalCost;
    }

    /**
     * Sets the total cost of the pizza order. 
     * Calcultated in PizzaOrderService
     * @param totalCost the total cost of the pizza order
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Returns the id of the pizza order.
     * @return the id of the pizza order
     */
    public void getId(long id) {
        this.id = id;
    }

    /**
     * Sets the id of the pizza order.
     * Calcultated in PizzaOrderCounterService
     * @param id the id of the pizza order
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Adds a pizza to the existing order.
     * @param Pizza the pizza to add
     */
    public void addPizza(Pizza pizza) {
        this.pizzas.add(pizza);
    }

    /**
     * Removes a pizza from the existing order.
     * @param Pizza the pizza to remove
     */
    public void removePizza(Pizza pizza) {
        this.pizzas.remove(pizza);
    }

    /**
     * Returns the list of pizzas in the order.
     * @return the list of pizzas in the order
     */
    public List<Pizza> getPizzas() {
        return this.pizzas;
    }

    /**
     * Once the order is complete, we can add id and total cost to the order
     * @param id
     * @param totalCost
     */
    public void checkOut(long id, double totalCost) {
        setId(id);
        setTotalCost(totalCost);
    }

}
