package com.github.bishopl.pizzatime.model;
/**
 * Represents a topping for a pizza, with a type and amount.
 */
public class PizzaTopping {
    private ToppingType toppingType;
    private ToppingAmount toppingAmount;

    /**
     * Constructs a new PizzaTopping with the given type and amount.
     * @param toppingType the type of topping
     * @param toppingAmount the amount of topping
     */
    public PizzaTopping(ToppingType toppingType, ToppingAmount toppingAmount) {
        this.toppingType = toppingType;
        this.toppingAmount = toppingAmount;
    }

    /**
     * Constructs a new PizzaTopping with the given type and a regular amount.
     * @param toppingType the type of topping
     */
    public PizzaTopping(ToppingType toppingType) {
        this(toppingType, ToppingAmount.REGULAR);
    }

    /**
     * Constructs a new PizzaTopping with the default type (cheese) and a regular amount.
     */
    public PizzaTopping() {
        this(ToppingType.CHEESE, ToppingAmount.REGULAR);
    }

    /**
     * Returns the type of this topping.
     * @return the type of topping
     */
    public ToppingType getToppingType() {
        return toppingType;
    }

    /**
     * Returns the amount of this topping.
     * @return the amount of topping
     */
    public ToppingAmount getToppingAmount() {
        return toppingAmount;
    }

    /**
     * Sets the amount of this topping.
     * @param toppingAmount the new amount of topping
     */
    public void setToppingAmount(ToppingAmount toppingAmount) {
        this.toppingAmount = toppingAmount;
    }

}