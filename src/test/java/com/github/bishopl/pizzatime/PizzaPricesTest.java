package com.github.bishopl.pizzatime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.github.bishopl.pizzatime.model.PizzaSize;
import com.github.bishopl.pizzatime.config.PizzaPrices;

public class PizzaPricesTest {
    
    @Test
    public void testGetBasePrice() {
        assertEquals(4.0, PizzaPrices.getBasePrice(PizzaSize.SMALL));
        assertEquals(9.0, PizzaPrices.getBasePrice(PizzaSize.MEDIUM));
        assertEquals(14.0, PizzaPrices.getBasePrice(PizzaSize.LARGE));
    }
    
}