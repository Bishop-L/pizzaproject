
/**
 * This service class is responsible for keeping track of the number of pizza orders.
 */
package com.github.bishopl.pizzatime.service;

import org.springframework.stereotype.Service;

@Service
public class PizzaOrderCounterService {
    private long orderCount = 0;

    public synchronized long getNextOrderNumber() {
        return ++orderCount;
    }

    public synchronized long getCurrentOrderNumber() {
        return orderCount;
    }
}
