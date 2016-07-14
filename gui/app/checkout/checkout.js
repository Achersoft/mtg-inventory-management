'use strict';

angular.module('main.checkout', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/checkout', {
        templateUrl: 'checkout/checkout.html',
        controller: 'CheckoutCtrl'
    });
}])

.controller('CheckoutCtrl', ['$scope', '$routeParams', 'CheckoutSvc', 'ngCart', function ($scope, $routeParams, checkoutSvc, ngCart) {
    $scope.cartSize = ngCart.$cart.items.length;
        
    $scope.checkout = function(){
        var index;
        var order = [];
        for (index = 0; index < ngCart.$cart.items.length; ++index) {
            order.push({'id':ngCart.$cart.items[index]._data.id,'condition':ngCart.$cart.items[index]._data.condition,'qty':ngCart.$cart.items[index]._quantity});
        }
        console.log(order);
    };
}])

.factory('CheckoutSvc',['$http', function($http){    
    var checkoutSvc={};

    return checkoutSvc;
}]);