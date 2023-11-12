package com.github.bishopl.pizzatime;

import com.github.bishopl.pizzatime.model.Pizza;
import com.github.bishopl.pizzatime.model.PizzaOrder;
import com.github.bishopl.pizzatime.model.PizzaSize;
import com.github.bishopl.pizzatime.model.PizzaTopping;
import com.github.bishopl.pizzatime.model.ToppingAmount;
import com.github.bishopl.pizzatime.model.ToppingType;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PizzaOrderTest {
    private Pizza cheesePizza;
    private Pizza supremePizza;
    private Pizza bestPizza;

    @Test
    public void testPizzaOrder() {
        // Create a pizza order with the pizzas
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(cheesePizza);
        pizzas.add(supremePizza);
        PizzaOrder order = new PizzaOrder(pizzas);

        // Check that the pizzas were added to the order
        assertEquals(pizzas, order.getPizzas());

        // Check that the date is set to the current time
        Date now = new Date();
        assertEquals(now.getTime() / 1000, order.getDate().getTime() / 1000);

        // Check that we can add and remove pizzas
        order.addPizza(bestPizza);
        assertEquals(3, order.getPizzas().size());
        order.removePizza(cheesePizza);
        assertEquals(2, order.getPizzas().size());

        // Check that we can set the id and total cost
        order.checkOut(1234, 27.0);
        assertEquals(1234, order.getId());
        assertEquals(27.0, order.getTotalCost());
    }
}