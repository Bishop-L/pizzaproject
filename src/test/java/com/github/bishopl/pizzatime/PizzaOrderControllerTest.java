package com.github.bishopl.pizzatime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.github.bishopl.pizzatime.controller.PizzaOrderController;
import com.github.bishopl.pizzatime.model.Pizza;
import com.github.bishopl.pizzatime.model.PizzaOrder;
import com.github.bishopl.pizzatime.model.PizzaSize;
import com.github.bishopl.pizzatime.model.PizzaTopping;
import com.github.bishopl.pizzatime.model.ToppingAmount;
import com.github.bishopl.pizzatime.model.ToppingType;
import com.github.bishopl.pizzatime.service.PizzaOrderCounterService;
import com.github.bishopl.pizzatime.service.PizzaOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@WebMvcTest(PizzaOrderController.class)
public class PizzaOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PizzaOrderCounterService pizzaOrderCounterService;

    @MockBean
    private PizzaOrderService pizzaOrderService;

    @Test
    public void testCreatePizzaOrder() throws Exception {
        // Arrange
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        long orderId = 1L;
        Pizza cheesePizza = new Pizza();
        ArrayList<PizzaTopping> bestToppings = new ArrayList<>();
        bestToppings.add(new PizzaTopping(ToppingType.CHEESE));
        bestToppings.add(new PizzaTopping(ToppingType.PINEAPPLES, ToppingAmount.EXTRA));
        bestToppings.add(new PizzaTopping(ToppingType.BACON));
        Pizza bestPizza = new Pizza(PizzaSize.SMALL, bestToppings); 
        
        PizzaOrder order = new PizzaOrder(orderId, Arrays.asList(cheesePizza, bestPizza));
        when(pizzaOrderCounterService.getNextOrderNumber()).thenReturn(orderId);
        when(pizzaOrderService.createPizzaOrder(orderId, Arrays.asList(cheesePizza, bestPizza))).thenReturn(order);

        // Act
        mockMvc.perform(post("/v1/app/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Arrays.asList(cheesePizza, bestPizza))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
                // .andExpect(jsonPath("$.id").exists())
                // .andExpect(jsonPath("$.date").exists())
                // .andExpect(jsonPath("$.pizzas[0].size").value("MEDIUM"))
                // .andExpect(jsonPath("$.pizzas[0].toppings[0].type").value("CHEESE"))
                // .andExpect(jsonPath("$.pizzas[0].toppings[0].amount").value("REGULAR"))
                // .andExpect(jsonPath("$.pizzas[1].size").value("SMALL"))
                // .andExpect(jsonPath("$.pizzas[1].toppings").isArray())
                // .andExpect(jsonPath("$.pizzas[1].toppings.length()").value(3))
                // .andExpect(jsonPath("$.pizzas[1].toppings[0].type").value("CHEESE"))
                // .andExpect(jsonPath("$.pizzas[1].toppings[0].amount").value("REGULAR"))
                // .andExpect(jsonPath("$.pizzas[1].toppings[1].type").value("PINEAPPLES"))
                // .andExpect(jsonPath("$.pizzas[1].toppings[1].amount").value("EXTRA"))
                // .andExpect(jsonPath("$.pizzas[1].toppings[2].type").value("BACON"))
                // .andExpect(jsonPath("$.pizzas[1].toppings[2].amount").value("REGULAR"))
                // .andExpect(jsonPath("$.totalCost").value(18.0));

        // Assert
        verify(pizzaOrderCounterService).getNextOrderNumber();
        //verify(pizzaOrderService).createPizzaOrder(orderId, Arrays.asList(cheesePizza, bestPizza));
    }
}