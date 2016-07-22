'use strict';

angular.module('main.orders', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/orders', {
        templateUrl: 'orders/orders.html',
        controller: 'OrderCtrl'
    })
    .when('/orders/:id', {
        templateUrl: 'orders/orderDetails.html',
        controller: 'OrderDetailCtrl'
    });
}])

.controller('OrderCtrl', ['$scope', '$routeParams', 'OrderSvc', 'RESOURCES', 'NgTableParams', function ($scope, $routeParams, orderSvc, RESOURCES, NgTableParams) {
    $scope.tableParams = new NgTableParams({
        page: 1,         
        count: 20     
    },
    {   total: 0, 
        counts: [], 
        getData: function ($defer, params) {
            orderSvc.getOrders().success(function (result) {
                params.total(result.length);
                $defer.resolve(result.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            });
        }
    });
}])

.controller('OrderDetailCtrl', ['$scope', '$routeParams', 'OrderSvc', 'RESOURCES', 'NgTableParams', function ($scope, $routeParams, orderSvc, RESOURCES, NgTableParams) {
    $scope.order = {"items":[]};
    
    $scope.tableParams = new NgTableParams({
        page: 1,         
        count: 20     
    },
    {   total: 0, 
        counts: [], 
        getData: function ($defer, params) {
            orderSvc.getOrder($routeParams.id).success(function (result) {
                $scope.order = result;
                params.total(result.items.length);
                $defer.resolve(result.items);
            });
        }
    });
    
    $scope.getTotal = function(){
        var total = 0;
        for(var i = 0; i < $scope.order.items.length; i++){
            var product = $scope.order.items[i];
            total += (product.price * product.qty);
        }
        return (($scope.order.discount)?total - (($scope.order.discount/100)*total):total).toFixed(2);
    };
}])

.factory('OrderSvc',['$http', 'RESOURCES', function($http, RESOURCES){    
    var cardSvc={};

    cardSvc.getOrders = function(){
        return $http.get(RESOURCES.REST_BASE_URL + '/orders');
    };
    
    cardSvc.getOrder = function(id){
        return $http.get(RESOURCES.REST_BASE_URL + '/orders/' + id);
    };

    return cardSvc;
}]);