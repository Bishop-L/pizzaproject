package com.github.bishopl.pizzatime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.github.bishopl.pizzatime.model.PizzaTopping;
import com.github.bishopl.pizzatime.model.ToppingType;
import com.github.bishopl.pizzatime.model.ToppingAmount;

public class PizzaToppingTest {
    @Test
    public void testGetDefaultToppingType() {
        PizzaTopping pizzaTopping = new PizzaTopping();
        assertEquals(ToppingType.CHEESE, pizzaTopping.getToppingType());
        assertEquals(ToppingAmount.REGULAR, pizzaTopping.getToppingAmount());
    }
    
    @Test
    public void testGetToppingType() {
        PizzaTopping pizzaTopping = new PizzaTopping(ToppingType.PEPPERONI);
        assertEquals(ToppingType.PEPPERONI, pizzaTopping.getToppingType());
        
    }

    @Test
    public void testGetToppingAmount() {
        PizzaTopping pizzaTopping = new PizzaTopping(ToppingType.MUSHROOMS, ToppingAmount.EXTRA);
        assertEquals(ToppingAmount.EXTRA, pizzaTopping.getToppingAmount());
    }

    @Test
    public void testSetToppingAmount() {
        PizzaTopping pizzaTopping = new PizzaTopping(ToppingType.OLIVES, ToppingAmount.LIGHT);
        pizzaTopping.setToppingAmount(ToppingAmount.EXTRA);
        assertEquals(ToppingAmount.EXTRA, pizzaTopping.getToppingAmount());
    }
}