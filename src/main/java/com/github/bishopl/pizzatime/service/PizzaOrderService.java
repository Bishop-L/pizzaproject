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

    /****************************
     * Cost calculation methods *
     *****************************/

    /**
     * Calculates the price of a given pizza order.
     * Called every time a pizza order is modified.
     * @param pizzaOrder the pizza order to calculate the price of
     * @return the price of the pizza order
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

    /**
     * Calculates the price of a given pizza.
     * @param pizza the pizza to calculate the price of
     * @return the price of the pizza
     */
    public static double getPricePerPizza(Pizza pizza) {
        double price = 0.0;
        price += PizzaPrices.getBasePrice(pizza.getPizzaSize());

        for (PizzaTopping topping : pizza.getToppings()) {
            price += ToppingPrices.getPrice(topping);
        }

        return price;
    }


    /****************************
     * PizzaOrder Methods       *
     *****************************/

    /**
     * Returns the pizza order with a given ID.
     * @param orderId the ID of the pizza order
     * @return the pizza order with the given ID
     */
    public PizzaOrder getPizzaOrderById(Long orderId) {
       for (PizzaOrder pizzaOrder : pizzaOrders) {
           if (pizzaOrder.getId() == orderId) {
               return pizzaOrder;
           }
       }
        return null;
    }

    /**
     * Creates a new pizza order with a given ID and list of pizzas.
     * @param id
     * @param pizzaOrder
     * @return the new pizza order
     */
    public PizzaOrder createPizzaOrder(long id, List<Pizza> pizzaOrder) {
        PizzaOrder newOrder = new PizzaOrder(id, pizzaOrder);
        pizzaOrders.add(newOrder);
        calculatePriceOfOrder(newOrder);
        return newOrder;
    }

    /**
     * Checks out the pizza order with a given ID. Finishing the order.
     * @param orderId the ID of the pizza order
     * @return the finished pizza order
     */
    public PizzaOrder checkout(Long orderId) {
        PizzaOrder pizzaOrder = getPizzaOrderById(orderId);
        calculatePriceOfOrder(pizzaOrder);
        //pizzaOrder.checkOut();
        return pizzaOrder;
    }

    /**
     * Deletes a pizza order with a given ID.
     * @param orderId the ID of the pizza order
     * @return true if the pizza order was deleted, false otherwise
     */
    public boolean deletePizzaOrder(Long orderId) {
        PizzaOrder pizzaOrder = getPizzaOrderById(orderId);

        if(pizzaOrder != null) {
            return pizzaOrders.remove(pizzaOrder);
        }
        return false;
    }


    /****************************
     * Pizza Methods            *
     *****************************/

    /**
     * Returns the list of pizzas for a given pizza order ID.
     * @param orderId the ID of the pizza order
     * @return the list of pizzas for the given pizza order ID
     */
    public List<Pizza> getPizzas(Long orderId) {
        return getPizzaOrderById(orderId).getPizzas();
    }

    /**
     * Returns the pizza at a given index for a given pizza order ID.
     * @param orderId the ID of the pizza order
     * @param pizzaIndex the index of the pizza
     * @return the pizza at the given index for the given pizza order ID
     */
    public Pizza getPizzaById(Long orderId, int pizzaIndex) {
        List<Pizza> pizzas = getPizzas(orderId);
        return pizzas.get(pizzaIndex);
    }

    /**
     * Creates a new pizza in a given pizza order.
     * @param orderId the ID of the pizza order
     * @return the pizza order with the new pizza
     */
    public PizzaOrder createPizza(Long orderId) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.addPizza(new Pizza());
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    /**
     * Deletes a pizza from a given pizza order.
     * @param orderId the ID of the pizza order
     * @param pizzaIndex the index of the pizza
     * @return the pizza order with the deleted pizza
     */
    public PizzaOrder deletePizza(Long orderId, int pizzaIndex) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.removePizza(thisOrder.getPizzas().get(pizzaIndex));
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    /**
     * Updates the size of a given pizza in a given pizza order.
     * @param orderId the ID of the pizza order
     * @param pizzaIndex the index of the pizza
     * @param pizzaSize the size to update to
     * @return the pizza order with the updated pizza size
     */
    public PizzaOrder updatePizzaSize(Long orderId, int pizzaIndex, PizzaSize pizzaSize) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.getPizzas().get(pizzaIndex).setPizzaSize(pizzaSize);
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    /****************************
     * Pizza Topping Methods    *
     *****************************/

    /**
     * Returns the list of toppings for a given pizza order ID and pizza index.
     * @param orderId the ID of the pizza order
     * @param pizzaIndex the index of the pizza
     * @return the list of toppings for the given pizza order ID and pizza index
     */
    public List<PizzaTopping> getPizzaToppings(Long orderId, int pizzaIndex) {
        return getPizzaById(orderId, pizzaIndex).getToppings();
    }

    /**
     * Adds a topping to a given pizza in a given pizza order.
     * @param orderId the ID of the pizza order
     * @param pizzaIndex the index of the pizza
     * @param newTopping the topping to add
     * @return the pizza order with the added topping
     */
    public PizzaOrder addPizzaTopping(Long orderId, int pizzaIndex, PizzaTopping newTopping) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.getPizzas().get(pizzaIndex).addTopping(newTopping);
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    /**
     * Removes a topping from a given pizza in a given pizza order.
     * @param orderId the ID of the pizza order
     * @param pizzaIndex the index of the pizza
     * @param removedTopping the topping to remove
     * @return the pizza order with the removed topping
     */
    public PizzaOrder removePizzaTopping(Long orderId, int pizzaIndex, PizzaTopping removedTopping) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.getPizzas().get(pizzaIndex).removeTopping(removedTopping);
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }

    /**
     * Updates the toppings for a given pizza in a given pizza order.
     * @param orderId the ID of the pizza order
     * @param pizzaIndex the index of the pizza
     * @param updatedToppings the list of toppings to update
     * @return the pizza order with the updated toppings
     */
    public PizzaOrder updatePizzaToppings(Long orderId, int pizzaIndex, List<PizzaTopping> updatedToppings) {
        PizzaOrder thisOrder = getPizzaOrderById(orderId);
        thisOrder.getPizzas().get(pizzaIndex).setTopping(updatedToppings);
        calculatePriceOfOrder(thisOrder);
        return thisOrder;
    }


    /****************************
     * Validation methods       *
     *****************************/
    
    /**
     * Checks if a given pizza order ID is valid.
     * @param orderId the ID of the pizza order to check
     * @return true if the pizza order ID is valid, false otherwise
     */
     public boolean isValidPizzaOrder(long orderId) {
        return getPizzaOrderById(orderId) != null;
    }
    
    /**
     * Checks if a given pizza index is valid for a given pizza order ID.
     * @param orderId the ID of the pizza order to check
     * @param pizzaIndex the index of the pizza to check
     * @return true if the pizza index is valid for the given pizza order ID, false otherwise
     */
    public boolean isValidPizza(long orderId, int pizzaIndex) {
        return isValidPizzaOrder(orderId) && pizzaIndex >= 0 && pizzaIndex < getPizzaOrderById(orderId).getPizzas().size();
    }

}