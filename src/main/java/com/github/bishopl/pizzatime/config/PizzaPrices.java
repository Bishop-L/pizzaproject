package com.github.bishopl.pizzatime.config;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the base prices for pizzas of different sizes.
 */
import com.github.bishopl.pizzatime.model.PizzaSize;

public class PizzaPrices {
    private static final Map<PizzaSize, Double> BASE_PRICE = new HashMap<>();
    
    static {
        BASE_PRICE.put(PizzaSize.SMALL, 4.0);
        BASE_PRICE.put(PizzaSize.MEDIUM, 9.0);
        BASE_PRICE.put(PizzaSize.LARGE, 14.0);
    }

    /**
     * Returns the base price for a pizza of a given size.
     * @param pizzaSize the size of the pizza
     * @return the base price of the pizza
     */
    public static double getBasePrice(PizzaSize pizzaSize) {
        return BASE_PRICE.get(pizzaSize);
    }

}