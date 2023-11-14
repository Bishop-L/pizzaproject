var app = angular.module('app', []);

// Pizza Order Controller
app.controller('PizzaOrderController', function ($http) {
    var $ctrl = this;
    $ctrl.apiUrl = '/v1/app/orders';

    $ctrl.startPizzaOrder = function (toppings) {
        return $http.post(`${$ctrl.apiUrl}`, toppings);
    };

    $ctrl.$onInit = function () {
        // Start a new pizza order on initialization
        var pizzas = [{}]; //Add the default pizzas
        $ctrl.startPizzaOrder(pizzas).then(function (response) {
            $ctrl.pizzaOrder = response.data;
            console.log(response.data);
        });
    };

    $ctrl.pizzaSizes = ['SMALL', 'MEDIUM', 'LARGE'];

    // Available toppings
    $ctrl.availableToppings = [
        { name: 'Cheese', amount: 2 }, 
        { name: 'Pepperoni', amount: 0 }, 
        { name: 'Sausage', amount: 0 },  
        { name: 'Bacon', amount: 0 },
        { name: 'Ham', amount: 0 },
        { name: 'Mushrooms', amount: 0 },
        { name: 'Olives', amount: 0 },
        { name: 'Onions', amount: 0 },
        { name: 'Peppers', amount: 0 },
        { name: 'Pineapples', amount: 0 }
    ];
});

// Pizza Order Component
app.component('pizzaOrder', {
    templateUrl: 'index.html',
    controller: 'PizzaOrderController',
    controllerAs: '$ctrl',
    $onInit: function () {
        var $ctrl = this;    

        // Start a new pizza order
        var pizzas = []; //Add empty array of pizzas to begin
        $ctrl.startPizzaOrder(pizzas).then(function (response) {
            $ctrl.newPizzaOrder = response.data;
        });
    },

    // Add other methods for updating, deleting, etc.
});
