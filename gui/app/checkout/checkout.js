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
    console.log(ngCart);
    $scope.checkout = function(){
       console.log(ngCart.$cart.items);
    };
}])

.factory('CheckoutSvc',['$http', function($http){    
    var checkoutSvc={};

    return checkoutSvc;
}]);