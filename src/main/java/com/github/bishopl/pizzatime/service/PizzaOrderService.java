/**
 * This class represents a service for calculating the price of a pizza order.
 * It contains a method to calculate the total price of an order based on the pizzas and toppings selected.
 */
package com.github.bishopl.pizzatime.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.github.bishopl.pizzatime.config.PizzaPrices;
import com.github.bishopl.pizzatime.config.ToppingPrices;
import com.github.bishopl.pizzatime.model.Pizza;
import com.github.bishopl.pizzatime.model.PizzaOrder;
import com.github.bishopl.pizzatime.model.PizzaTopping;

@Service
public class PizzaOrderService {
    public double calculatePriceOfOrder(PizzaOrder pizzaOrder) {
        ArrayList<Pizza> pizzas = (ArrayList<Pizza>) pizzaOrder.getPizzas();
        double total = 0.0;

        for (Pizza pizza : pizzas) {
            total += getPricePerPizza(pizza);
        }
        
        return total;

    }

    public static double getPricePerPizza(Pizza pizza) {
        double price = 0.0;
        price += PizzaPrices.getBasePrice(pizza.getPizzaSize());

        for (PizzaTopping topping : pizza.getToppings()) {
            price += ToppingPrices.getPrice(topping);
        }

        return price;
    }
}