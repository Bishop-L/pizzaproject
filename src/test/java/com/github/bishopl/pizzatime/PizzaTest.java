package com.github.bishopl.pizzatime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.github.bishopl.pizzatime.model.Pizza;
import com.github.bishopl.pizzatime.model.PizzaTopping;
import com.github.bishopl.pizzatime.model.PizzaSize;
import com.github.bishopl.pizzatime.model.ToppingType;
import com.github.bishopl.pizzatime.model.ToppingAmount;



public class PizzaTest {
    @Test
    public void testPizzaConstructor() {
        Pizza pizza = new Pizza(PizzaSize.LARGE, List.of(new PizzaTopping(ToppingType.PEPPERONI)));
        assertEquals(PizzaSize.LARGE, pizza.getPizzaSize());
        assertEquals(1, pizza.getToppings().size());
        assertEquals("PEPPERONI", pizza.getToppings().get(0).getToppingType().name());
        assertEquals("REGULAR", pizza.getToppings().get(0).getToppingAmount().name());
    }

    @Test
    public void testDefaultPizzaConstructor() {
        Pizza pizza = new Pizza();
        assertEquals(PizzaSize.MEDIUM, pizza.getPizzaSize());
        assertEquals(1, pizza.getToppings().size());
        assertEquals("CHEESE", pizza.getToppings().get(0).getToppingType().name());
        assertEquals("REGULAR", pizza.getToppings().get(0).getToppingAmount().name());
    }

    @Test
    public void testSetPizzaSize() {
        Pizza pizza = new Pizza(PizzaSize.SMALL);
        pizza.setPizzaSize(PizzaSize.LARGE);
        assertEquals(PizzaSize.LARGE, pizza.getPizzaSize());
    }

    @Test
    public void testAddTopping() {
        Pizza pizza = new Pizza(PizzaSize.MEDIUM);
        PizzaTopping topping = new PizzaTopping(ToppingType.MUSHROOMS, ToppingAmount.EXTRA);
        pizza.addTopping(topping);
        assertTrue(pizza.getToppings().contains(topping));
        
        // Adding the same topping with a different amount should update the amount
        PizzaTopping updatedTopping = new PizzaTopping(ToppingType.MUSHROOMS, ToppingAmount.LIGHT);
        pizza.addTopping(updatedTopping);
        // Cheese is aready on the pizza, so there should be 2 toppings
        assertEquals(2, pizza.getToppings().size());
        assertEquals("LIGHT", pizza.getToppings().get(1).getToppingAmount().name());
    }

    @Test
    public void testRemoveTopping() {
        Pizza pizza = new Pizza(PizzaSize.LARGE);
        PizzaTopping topping = new PizzaTopping(ToppingType.ONIONS);
        pizza.addTopping(topping);
        assertTrue(pizza.getToppings().contains(topping));
        
        pizza.removeTopping(topping);
        assertFalse(pizza.getToppings().contains(topping));
    }

    @Test
    public void testAddTopping_existingTopping() {
        Pizza pizza = new Pizza(PizzaSize.MEDIUM);
        PizzaTopping topping = new PizzaTopping(ToppingType.MUSHROOMS, ToppingAmount.EXTRA);
        pizza.addTopping(topping);
        assertTrue(pizza.getToppings().contains(topping));

        // Adding the same topping with a different amount should update the amount
        PizzaTopping updatedTopping = new PizzaTopping(ToppingType.MUSHROOMS, ToppingAmount.LIGHT);
        pizza.addTopping(updatedTopping);
        assertEquals(2, pizza.getToppings().size()); // Cheese is aready on the pizza, so there should be 2 toppings
        assertEquals("LIGHT", pizza.getToppings().get(1).getToppingAmount().name());
    }

    @Test
    public void testAddTopping_newTopping() {
        Pizza pizza = new Pizza(PizzaSize.MEDIUM);
        PizzaTopping topping = new PizzaTopping(ToppingType.MUSHROOMS, ToppingAmount.EXTRA);
        pizza.addTopping(topping);
        assertTrue(pizza.getToppings().contains(topping));

        PizzaTopping newTopping = new PizzaTopping(ToppingType.OLIVES, ToppingAmount.EXTRA);
        pizza.addTopping(newTopping);
        assertTrue(pizza.getToppings().contains(newTopping));
        assertEquals(3, pizza.getToppings().size()); // Cheese is aready on the pizza, so there should be 3 toppings
    }
}