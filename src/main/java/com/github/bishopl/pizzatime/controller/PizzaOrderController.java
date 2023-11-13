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
import java.util.ArrayList;

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

    /*
    * Orders
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

    @PostMapping
    public ResponseEntity<PizzaOrder> createPizzaOrder(@RequestBody List<Pizza> pizzaOrder) {
        long id = pizzaOrderCounterService.getNextOrderNumber();
        PizzaOrder createdOrder = pizzaOrderService.createPizzaOrder(id, pizzaOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PostMapping("/{orderId}/checkout")
    public ResponseEntity<PizzaOrder> checkoutOrder(@PathVariable Long orderId) {
        PizzaOrder checkedOutOrder = pizzaOrderService.checkout(orderId);

        if (checkedOutOrder == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(checkedOutOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deletePizzaOrder(@PathVariable Long orderId) {
        boolean deleted = pizzaOrderService.deletePizzaOrder(orderId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
    * Pizzas
    */
    @GetMapping("/{orderId}/pizzas")
    public ResponseEntity<List<Pizza>> getPizzas(@PathVariable Long orderId) {
        if (!pizzaOrderService.isValidPizzaOrder(orderId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaOrderService.getPizzas(orderId));
    }

    @GetMapping("/{orderId}/pizzas/{pizzaIndex}")
    public ResponseEntity<Pizza> getPizzaById(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex) {
        
        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaOrderService.getPizzaById(orderId, pizzaIndex));
    }

    @PostMapping("/{orderId}/pizzas/{pizzaIndex}")
    public ResponseEntity<PizzaOrder> createPizza(@PathVariable Long orderId){
        if (!pizzaOrderService.isValidPizzaOrder(orderId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaOrderService.createPizza(orderId));
    }

    @DeleteMapping("/{orderId}/pizzas/{pizzaIndex}")
    public ResponseEntity<PizzaOrder>  deletePizza(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex) {
        
        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaOrderService.deletePizza(orderId, pizzaIndex));
    }

    @PatchMapping("/{orderId}/pizzas/{pizzaIndex}")
    public ResponseEntity<PizzaOrder> updatePizza(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex,
            @RequestBody PizzaSize pizzaSize) {
        
        if (!pizzaOrderService.isValidPizza(orderId, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaOrderService.updatePizzaSize(orderId, pizzaIndex, pizzaSize));
    }

    /*
    * Toppings
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
    
    @DeleteMapping("/{orderId}/pizzas/{pizzaIndex}/toppings")
    public ResponseEntity<PizzaOrder> deletePizzaTopping(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex,
            @RequestBody PizzaTopping removedTopping) {
        
        if (!pizzaOrderService.isValidPizza(pizzaIndex, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(pizzaOrderService.removePizzaTopping(orderId, pizzaIndex, removedTopping));
    }
    
    
    @PatchMapping("/{orderId}/pizzas/{pizzaIndex}/toppings")
    public ResponseEntity<PizzaOrder> updatePizzaToppings(
            @PathVariable Long orderId,
            @PathVariable int pizzaIndex,
            @RequestBody List<PizzaTopping> updatedToppings) {
        
        if (!pizzaOrderService.isValidPizza(pizzaIndex, pizzaIndex)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(pizzaOrderService.updatePizzaToppings(orderId, pizzaIndex, updatedToppings));
    }
}
