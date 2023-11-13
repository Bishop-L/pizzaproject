/**
 * This class represents a service for PizzaOrder objects.
 * Includes methods for creation, modification, and calculating the price of a pizza order.
 */
package com.github.bishopl.pizzatime.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

import com.github.bishopl.pizzatime.config.PizzaPrices;
import com.github.bishopl.pizzatime.config.ToppingPrices;
import com.github.bishopl.pizzatime.model.Pizza;
import com.github.bishopl.pizzatime.model.PizzaOrder;
import com.github.bishopl.pizzatime.model.PizzaSize;
import com.github.bishopl.pizzatime.model.PizzaTopping;

@Service
public class PizzaOrderService {

    private final List<PizzaOrder> pizzaOrders = new ArrayList<>();

    /*
     * Cost calculation methods
     */
    public static double calculatePriceOfOrder(PizzaOrder pizzaOrder) {
        ArrayList<Pizza> pizzas = (ArrayList<Pizza>) pizzaOrder.getPizzas();
        double total = 0.0;

        for (Pizza pizza : pizzas) {
            total += getPricePerPizza(pizza);
        }
        
        pizzaOrder.setTotalCost(total);
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

    /*
     * PizzaOrder Methods
     */
    public PizzaOrder getPizzaOrderById(Long orderId) {
       for (PizzaOrder pizzaOrder : pizzaOrders) {
           if (pizzaOrder.getId() == orderId) {
               return pizzaOrder;
           }
       }
        return null;
    }

    public PizzaOrder createPizzaOrder(long id, List<Pizza> pizzaOrder) {
        PizzaOrder newOrder = new PizzaOrder(id, pizzaOrder);
        pizzaOrders.add(newOrder);
        calculatePriceOfOrder(newOrder);
        return newOrder;
    }

    public PizzaOrder checkout(Long orderId) {
        PizzaOrder pizzaOrder = getPizzaOrderById(orderId);
        calculatePriceOfOrder(pizzaOrder);
        //pizzaOrder.checkOut();
        return pizzaOrder;
    }

    public boolean deletePizzaOrder(Long orderId) {
        PizzaOrder pizzaOrder = getPizzaOrderById(orderId);

        if(pizzaOrder != null) {
            return pizzaOrders.remove(pizzaOrder);
        }
        return false;
    }

    /*
     * Pizza Methods
     */
    public List<Pizza> getPizzas(Long orderId) {
        return getPizzaOrderById(orderId).getPizzas();
    }

    public Pizza getPizzaById(Long orderId, int pizzaIndex) {
        List<Pizza> pizzas = getPizzas(orderId);
        return pizzas.get(pizzaIndex);
    }

    public PizzaOrder createPizza(Long orderId) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.addPizza(new Pizza());
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    public PizzaOrder deletePizza(Long orderId, int pizzaIndex) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.removePizza(thisOrder.getPizzas().get(pizzaIndex));
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    public PizzaOrder updatePizzaSize(Long orderId, int pizzaIndex, PizzaSize pizzaSize) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.getPizzas().get(pizzaIndex).setPizzaSize(pizzaSize);
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    /*
     * PizzaTopping Methods
     */
    public List<PizzaTopping> getPizzaToppings(Long orderId, int pizzaIndex) {
        return getPizzaById(orderId, pizzaIndex).getToppings();
    }

    public PizzaOrder addPizzaTopping(Long orderId, int pizzaIndex, PizzaTopping newTopping) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.getPizzas().get(pizzaIndex).addTopping(newTopping);
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    public PizzaOrder removePizzaTopping(Long orderId, int pizzaIndex, PizzaTopping removedTopping) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.getPizzas().get(pizzaIndex).removeTopping(removedTopping);
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    public PizzaOrder updatePizzaToppings(Long orderId, int pizzaIndex, List<PizzaTopping> updatedToppings) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.getPizzas().get(pizzaIndex).setTopping(updatedToppings);
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    /*
     * Validation methods
     */

    public boolean isValidPizzaOrder(long orderId) {
        return getPizzaOrderById(orderId) != null;
    }

    public boolean isValidPizza(long orderId, int pizzaIndex) {
        return isValidPizzaOrder(orderId) && pizzaIndex >= 0 && pizzaIndex < getPizzaOrderById(orderId).getPizzas().size();
    }

}