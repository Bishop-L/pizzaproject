package com.github.bishopl.pizzatime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.github.bishopl.pizzatime.model.Pizza;
import com.github.bishopl.pizzatime.model.PizzaOrder;
import com.github.bishopl.pizzatime.model.PizzaSize;
import com.github.bishopl.pizzatime.model.PizzaTopping;
import com.github.bishopl.pizzatime.model.ToppingAmount;
import com.github.bishopl.pizzatime.model.ToppingType;
import com.github.bishopl.pizzatime.service.PizzaOrderService;

public class PizzaOrderServiceTest {
    private PizzaOrderService pizzaOrderService;
    private Pizza cheesePizza;
    private Pizza supremePizza;
    private Pizza bestPizza;
    
    @BeforeEach
    public void setUp() {
        pizzaOrderService = new PizzaOrderService();
    }

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

        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.addPizza(cheesePizza);  // 10.0
        assertEquals(10.0, PizzaOrderService.getPricePerPizza(cheesePizza), 0.001);
        pizzaOrder.addPizza(supremePizza); // 19.0
        assertEquals(19.0, PizzaOrderService.getPricePerPizza(supremePizza), 0.001);
        pizzaOrder.addPizza(bestPizza); // 8.0
        assertEquals(8.0, PizzaOrderService.getPricePerPizza(bestPizza), 0.001);
        double expectedPrice = 37.0;
        double actualPrice = PizzaOrderService.calculatePriceOfOrder(pizzaOrder);
        assertEquals(expectedPrice, actualPrice, 0.001);
    }

    @Test
    public void testCreatePizzaOrder() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(PizzaSize.SMALL, new ArrayList<>()));
        pizzas.add(new Pizza(PizzaSize.MEDIUM, new ArrayList<>()));
        PizzaOrder pizzaOrder = pizzaOrderService.createPizzaOrder(1L, pizzas);
        assertNotNull(pizzaOrder);
        assertEquals(1L, pizzaOrder.getId());
        assertEquals(pizzas, pizzaOrder.getPizzas());
    }

    @Test
    public void testGetPizzaOrderById() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(PizzaSize.SMALL, new ArrayList<>()));
        pizzas.add(new Pizza(PizzaSize.MEDIUM, new ArrayList<>()));
        PizzaOrder pizzaOrder = pizzaOrderService.createPizzaOrder(1L, pizzas);
        PizzaOrder retrievedPizzaOrder = pizzaOrderService.getPizzaOrderById(1L);
        assertNotNull(retrievedPizzaOrder);
        assertEquals(pizzaOrder, retrievedPizzaOrder);
    }

    @Test
    public void testGetPizzaOrderByIdNotFound() {
        PizzaOrder retrievedPizzaOrder = pizzaOrderService.getPizzaOrderById(1L);
        assertNull(retrievedPizzaOrder);
    }

    @Test
    public void testDeletePizzaOrder() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(PizzaSize.SMALL, new ArrayList<>()));
        pizzas.add(new Pizza(PizzaSize.MEDIUM, new ArrayList<>()));
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        boolean deleted = pizzaOrderService.deletePizzaOrder(1L);
        assertTrue(deleted);
        PizzaOrder retrievedPizzaOrder = pizzaOrderService.getPizzaOrderById(1L);
        assertNull(retrievedPizzaOrder);
    }

    @Test
    public void testDeletePizzaOrderNotFound() {
        boolean deleted = pizzaOrderService.deletePizzaOrder(1L);
        assertFalse(deleted);
    }

    @Test
    public void testGetPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(PizzaSize.SMALL, new ArrayList<>()));
        pizzas.add(new Pizza(PizzaSize.MEDIUM, new ArrayList<>()));
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        List<Pizza> retrievedPizzas = pizzaOrderService.getPizzas(1L);
        assertNotNull(retrievedPizzas);
        assertEquals(pizzas, retrievedPizzas);
    }

    @Test
    public void testGetPizzaById() {
        List<Pizza> pizzas = new ArrayList<>();
        Pizza pizza1 = new Pizza(PizzaSize.SMALL, new ArrayList<>());
        Pizza pizza2 = new Pizza(PizzaSize.MEDIUM, new ArrayList<>());
        pizzas.add(pizza1);
        pizzas.add(pizza2);
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        Pizza retrievedPizza = pizzaOrderService.getPizzaById(1L, 0);
        assertNotNull(retrievedPizza);
        assertEquals(pizza1, retrievedPizza);
    }

    @Test
    public void testCreatePizza() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(PizzaSize.SMALL, new ArrayList<>()));
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        PizzaOrder updatedPizzaOrder = pizzaOrderService.createPizza(1L);
        assertNotNull(updatedPizzaOrder);
        assertEquals(2, updatedPizzaOrder.getPizzas().size());
    }

    @Test
    public void testDeletePizza() {
        List<Pizza> pizzas = new ArrayList<>();
        Pizza pizza1 = new Pizza(PizzaSize.SMALL, new ArrayList<>());
        Pizza pizza2 = new Pizza(PizzaSize.MEDIUM, new ArrayList<>());
        pizzas.add(pizza1);
        pizzas.add(pizza2);
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        PizzaOrder updatedPizzaOrder = pizzaOrderService.deletePizza(1L, 0);
        assertNotNull(updatedPizzaOrder);
        assertEquals(1, updatedPizzaOrder.getPizzas().size());
        assertFalse(updatedPizzaOrder.getPizzas().contains(pizza1));
    }

    @Test
    public void testUpdatePizzaSize() {
        List<Pizza> pizzas = new ArrayList<>();
        Pizza pizza1 = new Pizza(PizzaSize.SMALL, new ArrayList<>());
        Pizza pizza2 = new Pizza(PizzaSize.MEDIUM, new ArrayList<>());
        pizzas.add(pizza1);
        pizzas.add(pizza2);
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        PizzaOrder updatedPizzaOrder = pizzaOrderService.updatePizzaSize(1L, 0, PizzaSize.LARGE);
        assertNotNull(updatedPizzaOrder);
        assertEquals(PizzaSize.LARGE, updatedPizzaOrder.getPizzas().get(0).getPizzaSize());
    }

    @Test
    public void testAddPizzaTopping() {
        List<Pizza> pizzas = new ArrayList<>();
        Pizza pizza1 = new Pizza(PizzaSize.SMALL, new ArrayList<>());
        pizzas.add(pizza1);
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        PizzaTopping newTopping = new PizzaTopping(ToppingType.CHEESE);
        PizzaOrder updatedPizzaOrder = pizzaOrderService.addPizzaTopping(1L, 0, newTopping);
        assertNotNull(updatedPizzaOrder);
        assertEquals(1, updatedPizzaOrder.getPizzas().get(0).getToppings().size());
        assertTrue(updatedPizzaOrder.getPizzas().get(0).getToppings().contains(newTopping));
    }

    @Test
    public void testRemovePizzaTopping() {
        List<Pizza> pizzas = new ArrayList<>();
        PizzaTopping topping1 = new PizzaTopping(ToppingType.CHEESE);
        PizzaTopping topping2 = new PizzaTopping(ToppingType.SAUSAGE);
        Pizza pizza1 = new Pizza(PizzaSize.SMALL, new ArrayList<>());
        pizza1.addTopping(topping1);
        pizza1.addTopping(topping2);
        pizzas.add(pizza1);
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        PizzaOrder updatedPizzaOrder = pizzaOrderService.removePizzaTopping(1L, 0, topping1);
        assertNotNull(updatedPizzaOrder);
        assertEquals(1, updatedPizzaOrder.getPizzas().get(0).getToppings().size());
        assertFalse(updatedPizzaOrder.getPizzas().get(0).getToppings().contains(topping1));
    }

    @Test
    public void testUpdatePizzaToppings() {
        List<Pizza> pizzas = new ArrayList<>();
        List<PizzaTopping> toppingList = new ArrayList<>();
        toppingList.add(new PizzaTopping(ToppingType.CHEESE));
        toppingList.add(new PizzaTopping(ToppingType.SAUSAGE));
        Pizza pizza1 = new Pizza(PizzaSize.SMALL, toppingList);
        pizzas.add(pizza1);
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        List<PizzaTopping> updatedToppings = new ArrayList<>();
        updatedToppings.add(new PizzaTopping(ToppingType.ONIONS));
        PizzaOrder updatedPizzaOrder = pizzaOrderService.updatePizzaToppings(1L, 0, updatedToppings);
        assertNotNull(updatedPizzaOrder);
        assertEquals(3, updatedPizzaOrder.getPizzas().get(0).getToppings().size());
        assertTrue(updatedPizzaOrder.getPizzas().get(0).getToppings().contains(updatedToppings.get(0)));
    }

    @Test
    public void testIsValidPizzaOrder() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(PizzaSize.SMALL, new ArrayList<>()));
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        boolean isValid = pizzaOrderService.isValidPizzaOrder(1L);
        assertTrue(isValid);
    }

    @Test
    public void testIsValidPizzaOrderNotFound() {
        boolean isValid = pizzaOrderService.isValidPizzaOrder(1L);
        assertFalse(isValid);
    }

    @Test
    public void testIsValidPizza() {
        List<Pizza> pizzas = new ArrayList<>();
        Pizza pizza1 = new Pizza(PizzaSize.SMALL, new ArrayList<>());
        Pizza pizza2 = new Pizza(PizzaSize.MEDIUM, new ArrayList<>());
        pizzas.add(pizza1);
        pizzas.add(pizza2);
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        boolean isValid = pizzaOrderService.isValidPizza(1L, 0);
        assertTrue(isValid);
    }

    @Test
    public void testIsValidPizzaNotFound() {
        List<Pizza> pizzas = new ArrayList<>();
        Pizza pizza1 = new Pizza(PizzaSize.SMALL, new ArrayList<>());
        Pizza pizza2 = new Pizza(PizzaSize.MEDIUM, new ArrayList<>());
        pizzas.add(pizza1);
        pizzas.add(pizza2);
        pizzaOrderService.createPizzaOrder(1L, pizzas);
        boolean isValid = pizzaOrderService.isValidPizza(1L, 2);
        assertFalse(isValid);
    }
}