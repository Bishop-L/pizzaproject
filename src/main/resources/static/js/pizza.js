var app = angular.module('app', []);

// Pizza Order Controller
app.controller('PizzaOrderController', function ($http) {
    var $ctrl = this;
    $ctrl.apiUrl = '/v1/app/orders';

    $ctrl.startPizzaOrder = function (pizzas) {
        return $http.post(`${$ctrl.apiUrl}`, pizzas);
    };

     // Available toppings
     $ctrl.availableToppings = [
        { type: 'Cheese', amount: 'NONE'}, 
        { type: 'Pepperoni', amount: 'NONE' }, 
        { type: 'Sausage', amount: 'NONE' },  
        { type: 'Bacon', amount: 'NONE' },
        { type: 'Ham', amount: 'NONE' },
        { type: 'Mushrooms', amount: 'NONE' },
        { type: 'Olives', amount: 'NONE' },
        { type: 'Onions', amount: 'NONE' },
        { type: 'Peppers', amount: 'NONE' },
        { type: 'Pineapples', amount: 'NONE' }
    ];

    // Pizza sizes
    $ctrl.pizzaSizes = ['SMALL', 'MEDIUM', 'LARGE'];  

    // Start a new pizza order on initialization
    $ctrl.$onInit = function () {
        var pizzas = [{}]; //Add the default pizzas
        $ctrl.startPizzaOrder(pizzas).then(function (response) {
            $ctrl.updatePizzaOrder(response);
        });
    };

    // Change pizza size
    $ctrl.changePizzaSize = function (pizzaSize) {
        var orderId = $ctrl.pizzaOrder.id;
        return $http.patch(`${$ctrl.apiUrl}/${orderId}/pizzas/0`, pizzaSize);
    };

    // Update or add a topping
    $ctrl.updatePizzaTopping = function (topping) {
        var orderId = $ctrl.pizzaOrder.id;
        return $http.post(`${$ctrl.apiUrl}/${orderId}/pizzas/0/toppings`, topping);
    };

    // Delete a topping ($http.delete does not support a body)
    $ctrl.deletePizzaTopping = function (topping) {
        var orderId = $ctrl.pizzaOrder.id;
        var config = {
            method: 'DELETE',
            url: `${$ctrl.apiUrl}/${orderId}/pizzas/0/toppings`,
            data: topping,
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        };
    
        return $http(config);
    };

    // Update available toppings based on the pizza order
    $ctrl.updateAvailableToppings = function (pizzaOrder) {
        angular.forEach(pizzaOrder.pizzas[0].toppings, function (responseTopping) {
            var matchingTopping = $ctrl.availableToppings.find(function (availableTopping) {
                return availableTopping.type.toUpperCase() === responseTopping.type;
            });

            if (matchingTopping) {
                matchingTopping.amount = responseTopping.amount;
                matchingTopping.isToppingSelected = responseTopping.amount !== 'NONE';
            }
        });
    };

    // Call right api call depending on the topping amount
    $ctrl.toppingChanged = function (topping) {
        var toppingData = {};
        toppingData['type'] = topping.type.toUpperCase();
        $ctrl.updatePizzaImage(topping);

        if(!topping.isToppingSelected){
            // Delete topping
            topping.amount = 'REGULAR';
            toppingData['amount'] = topping.amount;
            $ctrl.deletePizzaTopping(toppingData).then(function (response) {
                $ctrl.updatePizzaOrder(response);
            });
            topping.amount = 'NONE';
        } else {
            // Update topping
            if(topping.amount === 'NONE'){
                topping.amount = 'REGULAR';
            }
            toppingData['amount'] = topping.amount;
            $ctrl.updatePizzaTopping(toppingData).then(function (response) {
                $ctrl.updatePizzaOrder(response);
            });
        }
    };

    // Update pizza size
    $ctrl.sizeChanged = function () {
        var size = $ctrl.pizzaOrder.pizzas[0].size.toUpperCase();

        $ctrl.changePizzaSize(size).then(function (response) {
            $ctrl.updatePizzaOrder(response);
        });

    }

    // Update the current pizza order
    $ctrl.updatePizzaOrder = function (response) {
        $ctrl.pizzaOrder = response.data;
        $ctrl.updateAvailableToppings(response.data);
    };  

    // Function for pizza image, only works for cheese and pepperoni
    $ctrl.updatePizzaImage = function (topping) {
            const pizzaImage = document.querySelector('.pizza-image');
            const pie = pizzaImage.querySelector('.pie');
        
            function removeClasses() {
                pie.classList.remove('pie-updated', 'pie-updated-pepperoni', 'pie-updated-pepperoni-no-cheese');
            }

            if (topping.type === 'Cheese') {
                var peppTrue = $ctrl.availableToppings.find(t => t.type === 'Pepperoni').isToppingSelected;
                if (!topping.isToppingSelected && peppTrue) {
                    removeClasses();
                    pie.classList.add('pie-updated-pepperoni-no-cheese');
                } else if(!topping.isToppingSelected && !peppTrue){
                    removeClasses();
                    pie.classList.add('pie-updated');
                } else if (topping.isToppingSelected && peppTrue) {
                    removeClasses();
                    pie.classList.add('pie-updated-pepperoni');                   
                } else {
                    removeClasses();
                }
            }
        
            if (topping.type === 'Pepperoni') {
                var cheeseTrue = $ctrl.availableToppings.find(t => t.type === 'Cheese').isToppingSelected;
                if (!topping.isToppingSelected && cheeseTrue) {
                    removeClasses();
                } else if(!topping.isToppingSelected && !cheeseTrue){
                    removeClasses();
                    pie.classList.add('pie-updated');
                } else if (topping.isToppingSelected && !cheeseTrue) {
                    removeClasses();
                    pie.classList.add('pie-updated-pepperoni-no-cheese');
                } else {
                    removeClasses();
                    pie.classList.add('pie-updated-pepperoni');
                }
            }
        };

        

});

// Pizza Order Component
app.component('pizzaOrder', {
    templateUrl: 'index.html',
    controller: 'PizzaOrderController',
    controllerAs: '$ctrl',
    $onInit: function () {
        var $ctrl = this;    
    },     
});
