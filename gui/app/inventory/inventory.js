'use strict';

angular.module('main.inventory', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/bulkAdd/', {
        templateUrl: 'inventory/bulkAdd.html',
        controller: 'InventoryCtrl'
    });
}])

.controller('InventoryCtrl', ['$scope', '$routeParams', 'NgTableParams', 'InventorySvc', function ($scope, $routeParams, NgTableParams, inventorySvc) {
    $scope.sets;
    $scope.languages;
    $scope.selectedSet;
    $scope.selectedLanguage;
    $scope.data;
    
    inventorySvc.getSets().then(function(response) {
        $scope.sets = response.data;
    });
    
    inventorySvc.getLanguages().then(function(response) {
        $scope.languages = response.data;
    });
    
    $scope.updateSetList = function(){
        inventorySvc.getCards($scope.selectedSet, $scope.selectedLanguage).then(function(response) {
            console.log(response.data);
            $scope.data = response.data;
        });
    };
    
    //cardSvc.getCardDetails($routeParams.cardId).success(function (data) {
   //     $scope.card = data;
  //  });
}])

.factory('InventorySvc',['$http', function($http){    
    var inventorySvc={};

    inventorySvc.getSets = function(){
        return $http.get('http://localhost:8080/enums/sets');
    };
    
    inventorySvc.getLanguages = function(){
        return $http.get('http://localhost:8080/enums/languages');
    };
    
    inventorySvc.getCards = function(setId, lang){
        return $http.get('http://localhost:8080/cards/setinventory/' + setId + '?language=' + lang);
    };

    return inventorySvc;
}]);