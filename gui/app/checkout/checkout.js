'use strict';

angular.module('main.checkout', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/checkout', {
        templateUrl: 'checkout/checkout.html',
        controller: 'CheckoutCtrl'
    });
}])

.controller('CheckoutCtrl', ['$scope', '$routeParams', 'CheckoutSvc', function ($scope, $routeParams, checkoutSvc) {

}])

.factory('CheckoutSvc',['$http', function($http){    
    var checkoutSvc={};

    return checkoutSvc;
}]);