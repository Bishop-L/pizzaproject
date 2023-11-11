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

        double mushroomsPrice = ToppingPrices.getPrice(ToppingType.MUSHROOMS, ToppingAmount.EXTRA);
        assertEquals(0.75, mushroomsPrice);

        double olivesPrice = ToppingPrices.getPrice(ToppingType.OLIVES, ToppingAmount.LIGHT);
        assertEquals(0.25, olivesPrice);
    }

    @Test
    void testSetPrice() {
        double originalCheesePrice = ToppingPrices.getPrice(ToppingType.CHEESE);
        ToppingPrices.setPrice(ToppingType.CHEESE, 1.5);
        double cheesePrice = ToppingPrices.getPrice(ToppingType.CHEESE);
        assertEquals(1.5, cheesePrice);

        // Reset the price to its original value
        ToppingPrices.setPrice(ToppingType.CHEESE, originalCheesePrice);

        double originalPineapplesPrice = ToppingPrices.getPrice(ToppingType.PINEAPPLES);
        ToppingPrices.setPrice(ToppingType.PINEAPPLES, 0.75);
        double pineapplesPrice = ToppingPrices.getPrice(ToppingType.PINEAPPLES);
        assertEquals(0.75, pineapplesPrice);

        // Reset the price to its original value
        ToppingPrices.setPrice(ToppingType.PINEAPPLES, originalPineapplesPrice);
    }

    @Test
    void testSetMultiplier() {
        double originalLightMultiplier = ToppingPrices.getMultiplier(ToppingAmount.LIGHT);
        ToppingPrices.setMultiplier(ToppingAmount.LIGHT, 0.25);
        double lightMultiplier = ToppingPrices.getMultiplier(ToppingAmount.LIGHT);
        assertEquals(0.25, lightMultiplier);

        // Reset the multiplier to its original value
        ToppingPrices.setMultiplier(ToppingAmount.LIGHT, originalLightMultiplier);

        double originalExtraMultiplier = ToppingPrices.getMultiplier(ToppingAmount.EXTRA);
        ToppingPrices.setMultiplier(ToppingAmount.EXTRA, 2.0);
        double extraMultiplier = ToppingPrices.getMultiplier(ToppingAmount.EXTRA);
        assertEquals(2.0, extraMultiplier);

        // Reset the multiplier to its original value
        ToppingPrices.setMultiplier(ToppingAmount.EXTRA, originalExtraMultiplier);
    }
}