package com.github.bishopl.pizzatime.config;

import java.util.HashMap;
import java.util.Map;

import com.github.bishopl.pizzatime.model.PizzaTopping;
import com.github.bishopl.pizzatime.model.ToppingType;
import com.github.bishopl.pizzatime.model.ToppingAmount;

/**
 * This class represents the prices and multipliers for different toppings.
 * It provides methods to get and set the prices and multipliers for each topping amount.
 */
public class ToppingPrices {
    private static final Map<ToppingType, Double> PRICES = new HashMap<>();
    private static final Map<ToppingAmount, Double> MULTIPLIERS = new HashMap<>();

    static {
        PRICES.put(ToppingType.CHEESE, 1.0);
        PRICES.put(ToppingType.PEPPERONI, 1.0);
        PRICES.put(ToppingType.HAM, 1.0);
        PRICES.put(ToppingType.SAUSAGE, 1.0);
        PRICES.put(ToppingType.BACON, 1.0);
        PRICES.put(ToppingType.MUSHROOMS, 0.5);
        PRICES.put(ToppingType.OLIVES, 0.5);
        PRICES.put(ToppingType.ONIONS, 0.5);
        PRICES.put(ToppingType.PEPPERS, 0.5);
        PRICES.put(ToppingType.PINEAPPLES, 0.5);
    }

    static {
        MULTIPLIERS.put(ToppingAmount.LIGHT, 0.5);
        MULTIPLIERS.put(ToppingAmount.REGULAR, 1.0);
        MULTIPLIERS.put(ToppingAmount.EXTRA, 1.5);
    }    
       
      
    /**
     * Calculates the price of a topping based on its type and amount.
     * @param toppingType the type of topping
     * @param toppingAmount the amount of topping
     * @return the price of the topping
     */
    public static double getPrice(ToppingType toppingType, ToppingAmount toppingAmount) {
        return PRICES.get(toppingType) * getMultiplier(toppingAmount);
    }

    /**
     * Returns the price of a pizza topping based on its type and amount.
     *
     * @param topping the PizzaTopping object containing the topping type and amount
     * @return the price of the topping
     */
    public static double getPrice(PizzaTopping topping) {
        return getPrice(topping.getToppingType(), topping.getToppingAmount());
    }

    /**
     * Returns the price of a topping based on its type defaulting to regular amount.
     *
     * @param toppingType the type of topping
     * @return the price of the topping
     * @see #getPrice(ToppingType, ToppingAmount)
     */
    public static double getPrice(ToppingType toppingType) {
        return getPrice(toppingType, ToppingAmount.REGULAR);
    }

    /**
     * Returns the multiplier for a given ToppingAmount.
     *
     * @param toppingAmount the ToppingAmount to get the multiplier for
     * @return the multiplier for the given ToppingAmount
     */
    private static double getMultiplier(ToppingAmount toppingAmount) {
        return MULTIPLIERS.get(toppingAmount);
    }
    
    /**
     * Sets the price for a given ToppingType.
     *
     * @param toppingType the ToppingType to set the price for
     * @param price the price to set for the ToppingType
     */
    // public static void setPrice(ToppingType toppingType, double price) {
    //     PRICES.put(toppingType, price);
    // }


    /**
     * Sets the multiplier for a given ToppingAmount.
     *
     * @param toppingAmount the ToppingAmount to set the multiplier for
     * @param multiplier the multiplier to set for the ToppingAmount
     */
    // public static void setMultiplier(ToppingAmount toppingAmount, double multiplier) {
    //     MULTIPLIERS.put(toppingAmount, multiplier);
    // }
  
}
