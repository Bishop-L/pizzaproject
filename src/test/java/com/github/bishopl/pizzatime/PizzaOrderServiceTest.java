package com.github.bishopl.pizzatime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import com.github.bishopl.pizzatime.model.Pizza;
import com.github.bishopl.pizzatime.model.PizzaOrder;
import com.github.bishopl.pizzatime.model.PizzaSize;
import com.github.bishopl.pizzatime.model.PizzaTopping;
import com.github.bishopl.pizzatime.model.ToppingAmount;
import com.github.bishopl.pizzatime.model.ToppingType;
import com.github.bishopl.pizzatime.service.PizzaOrderService;

public class PizzaOrderServiceTest {
    private Pizza cheesePizza;
    private Pizza supremePizza;
    private Pizza bestPizza;
    
    @Test
    public void testCalculatePriceOfOrder() {
        // Create some pizzas for testing
        cheesePizza = new Pizza();

        ArrayList<PizzaTopping> supremeToppings = new ArrayList<PizzaTopping>();
        supremeToppings.add(new PizzaTopping(ToppingType.CHEESE));
        supremeToppings.add(new PizzaTopping(ToppingType.SAUSAGE));
        supremeToppings.add(new PizzaTopping(ToppingType.MUSHROOMS, ToppingAmount.LIGHT));
        supremeToppings.add(new PizzaTopping(ToppingType.PEPPERS));
        supremeToppings.add(new PizzaTopping(ToppingType.ONIONS));
        supremePizza = new Pizza(PizzaSize.LARGE, supremeToppings);

        ArrayList<PizzaTopping> bestToppings = new ArrayList<>();
        bestToppings.add(new PizzaTopping(ToppingType.CHEESE));
        bestToppings.add(new PizzaTopping(ToppingType.PINEAPPLES, ToppingAmount.EXTRA));
        bestToppings.add(new PizzaTopping(ToppingType.BACON));
        bestPizza = new Pizza(PizzaSize.SMALL, bestToppings);   

        PizzaOrderService pizzaOrderService = new PizzaOrderService();
        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.addPizza(cheesePizza);  // 10.0
        assertEquals(10.0, PizzaOrderService.getPricePerPizza(cheesePizza), 0.001);
        pizzaOrder.addPizza(supremePizza); // 19.0
        assertEquals(19.0, PizzaOrderService.getPricePerPizza(supremePizza), 0.001);
        pizzaOrder.addPizza(bestPizza); // 8.0
        assertEquals(8.0, PizzaOrderService.getPricePerPizza(bestPizza), 0.001);
        double expectedPrice = 37.0;
        double actualPrice = pizzaOrderService.calculatePriceOfOrder(pizzaOrder);
        assertEquals(expectedPrice, actualPrice, 0.001);
    }
}