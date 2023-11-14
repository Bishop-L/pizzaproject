package com.github.bishopl.pizzatime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.github.bishopl.pizzatime.model.ToppingType;
import com.github.bishopl.pizzatime.model.ToppingAmount;
import com.github.bishopl.pizzatime.config.ToppingPrices;

public class ToppingPricesTest {
    @Test
    void testGetPrice() {
        double cheesePrice = ToppingPrices.getPrice(ToppingType.CHEESE);
        assertEquals(1.0, cheesePrice);

        double baconPrice = ToppingPrices.getPrice(ToppingType.BACON, ToppingAmount.EXTRA);
        assertEquals(2.25, baconPrice);

        double olivesPrice = ToppingPrices.getPrice(ToppingType.OLIVES, ToppingAmount.LIGHT);
        assertEquals(0.5, olivesPrice);
    }
}