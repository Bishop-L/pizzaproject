/**
 * This class represents the REST API endpoints for managing pizza orders.
 * It provides methods for retrieving, creating, updating, and deleting pizza orders, pizzas, and toppings.
 * The endpoints are versioned under "/v1/app/orders".
 */
package com.github.bishopl.pizzatime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.github.bishopl.pizzatime.model.Pizza;
import com.github.bishopl.pizzatime.model.PizzaOrder;
import com.github.bishopl.pizzatime.model.PizzaSize;
import com.github.bishopl.pizzatime.model.PizzaTopping;
import com.github.bishopl.pizzatime.service.PizzaOrderCounterService;
import com.github.bishopl.pizzatime.service.PizzaOrderService;

@RestController
@RequestMapping("/v1/app/orders")
public class PizzaOrderController {

    private final PizzaOrderService pizzaOrderService;
    private final PizzaOrderCounterService pizzaOrderCounterService;

    @Autowired
    public PizzaOrderController(PizzaOrderService pizzaOrderService, PizzaOrderCounterService pizzaOrderCounterService) {
        this.pizzaOrderService = pizzaOrderService;
        this.pizzaOrderCounterService = pizzaOrderCounterService;
    }

    
    
    
    /***************************************
    * Orders                               *
    *****************************************/

    /**
     * Retrieves a pizza order by its ID.
     *
     * @param orderId the ID of the pizza order to retrieve
     * @return a ResponseEntity containing the pizza order if found, or a 404 Not Found status if not found
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<PizzaOrder> getPizzaOrderById(@PathVariable Long orderId) {
        PizzaOrder pizzaOrder = pizzaOrderService.getPizzaOrderById(orderId);
        if (pizzaOrder != null) {
            return ResponseEntity.ok(pizzaOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new pizza order.
     *
     * @param pizzaOrder the pizza order to create
     * @return a ResponseEntity containing the created pizza order and a 201 Created status
     */
    @PostMapping
    public ResponseEntity<PizzaOrder> createPizzaOrder(@RequestBody List<Pizza> pizzaOrder) {
        long id = pizzaOrderCounterService.getNextOrderNumber();
        PizzaOrder createdOrder = pizzaOrderService.createPizzaOrder(id, pizzaOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Updates a pizza order.
     *
     * @param orderId    the ID of the pizza order to update
     * @param pizzaOrder the pizza order to update
     * @return a ResponseEntity containing the updated pizza order if found, or a 404 Not Found status if not found
     */
    @PostMapping("/{orderId}/checkout")
    public ResponseEntity<PizzaOrder> checkoutOrder(@PathVariable Long orderId) {
        PizzaOrder checkedOutOrder = pizzaOrderService.checkout(orderId);

        if (checkedOutOrder == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(checkedOutOrder);
    }

    /**
     * Deletes a pizza order.
     *
     * @param orderId the ID of the pizza order to delete
     * @return a ResponseEntity with a 204 No Content status if the pizza order was deleted, or a 404 Not Found status if not found
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deletePizzaOrder(@PathVariable Long orderId) {
        boolean deleted = pizzaOrderService.deletePizzaOrder(orderId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /***************************************
    * Pizza                                *
    *****************************************/
    
    /**
     * Retrieves all pizzas in an order
     *
     * @param orderId   the ID of the pizza order to retrieve
     * @param pizzaIndex the index of the pizza to retrieve
     * @return a ResponseEntity containing the pizza if found, or a 404 Not Found status if not found
     */
    @GetMapping("/{orderId}/pizzas")
    public ResponseEntity<List<Pizza>> getPizzas(@PathVariable Long orderId) {
        if (!pizzaOrderService.isValidPizzaOrder(orderId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaOrderService.getPizzas(orderId));
    }

    /**
     * Retrieves a pizza by its ID.
     *
     * @param orderId   the ID of the pizza order to retrieve
     * @param pizzaIndex the index of the pizza to retrieve
     * @return a ResponseEntity containing the pizza if found, or a 404 Not Found status if not found
     */
    @GetMapping("/{orderId}/pizzas/{pizzaIndex}")
    public ResponseEntity<Pizza> getPizzaById(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex) {
        
        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaOrderService.getPizzaById(orderId, pizzaIndex));
    }

    /**
     * Creates a new pizza.
     *
     * @param orderId   the ID of the pizza order to create the pizza in
     * @param pizzaIndex the index of the pizza to create
     * @return a ResponseEntity containing the created pizza and a 201 Created status
     */
    @PostMapping("/{orderId}/pizzas/{pizzaIndex}")
    public ResponseEntity<PizzaOrder> createPizza(@PathVariable Long orderId){
        if (!pizzaOrderService.isValidPizzaOrder(orderId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaOrderService.createPizza(orderId));
    }

    /**
     * Deletes a pizza.
     *
     * @param orderId   the ID of the pizza order to delete the pizza from
     * @param pizzaIndex the index of the pizza to delete
     * @return a ResponseEntity containing the updated pizza order if found, or a 404 Not Found status if not found
     */
    @DeleteMapping("/{orderId}/pizzas/{pizzaIndex}")
    public ResponseEntity<PizzaOrder>  deletePizza(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex) {
        
        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaOrderService.deletePizza(orderId, pizzaIndex));
    }

    /**
     * Updates a pizza's size, only attribute we update for now.
     *
     * @param orderId   the ID of the pizza order to update the pizza in
     * @param pizzaIndex the index of the pizza to update
     * @param pizzaSize the new size of the pizza
     * @return a ResponseEntity containing the updated pizza order if found, or a 404 Not Found status if not found
     */
    @PatchMapping("/{orderId}/pizzas/{pizzaIndex}")
    public ResponseEntity<PizzaOrder> updatePizza(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex,
            @RequestBody String pizzaSize) {
        
        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }

        try {
            PizzaSize size = PizzaSize.valueOf(pizzaSize);

            return ResponseEntity.ok(pizzaOrderService.updatePizzaSize(orderId, pizzaIndex, size));
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); 
        }
        return null;
    }


    /***************************************
    * Topping                              *
    *****************************************/

    /**
     * Retrieves all toppings on a pizza.
     *
     * @param orderId   the ID of the pizza order to retrieve the pizza from
     * @param pizzaIndex the index of the pizza to retrieve the toppings from
     * @return a ResponseEntity containing the toppings if found, or a 404 Not Found status if not found
     */
    @GetMapping("/{orderId}/pizzas/{pizzaIndex}/toppings")
    public ResponseEntity<List<PizzaTopping>> getPizzaToppings(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex) {

        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pizzaOrderService.getPizzaToppings(orderId, pizzaIndex));
    }

    /**
     * Adds a topping to a pizza.
     *
     * @param orderId   the ID of the pizza order to add the topping to
     * @param pizzaIndex the index of the pizza to add the topping to
     * @param newTopping the topping to add
     * @return a ResponseEntity containing the updated pizza order if found, or a 404 Not Found status if not found
     */
    @PostMapping("/{orderId}/pizzas/{pizzaIndex}/toppings")
    public ResponseEntity<PizzaOrder> addPizzaTopping(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex,
            @RequestBody PizzaTopping newTopping) {
        
        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pizzaOrderService.addPizzaTopping(orderId, pizzaIndex, newTopping));
    }
    
    /**
     * Removes a topping from a pizza.
     *
     * @param orderId   the ID of the pizza order to remove the topping from
     * @param pizzaIndex the index of the pizza to remove the topping from
     * @param removedTopping the topping to remove
     * @return a ResponseEntity containing the updated pizza order if found, or a 404 Not Found status if not found
     */
    @DeleteMapping("/{orderId}/pizzas/{pizzaIndex}/toppings")
    public ResponseEntity<PizzaOrder> deletePizzaTopping(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex,
            @RequestBody PizzaTopping removedTopping) {
        
        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(pizzaOrderService.removePizzaTopping(orderId, pizzaIndex, removedTopping));
    }
    
    /**
     * Updates a pizza's toppings.
     *
     * @param orderId   the ID of the pizza order to update the pizza in
     * @param pizzaIndex the index of the pizza to update
     * @param updatedToppings the new toppings of the pizza
     * @return a ResponseEntity containing the updated pizza order if found, or a 404 Not Found status if not found
     */
    @PatchMapping("/{orderId}/pizzas/{pizzaIndex}/toppings")
    public ResponseEntity<PizzaOrder> updatePizzaToppings(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex,
            @RequestBody List<PizzaTopping> updatedToppings) {
        
        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(pizzaOrderService.updatePizzaToppings(orderId, pizzaIndex, updatedToppings));
    }
}
