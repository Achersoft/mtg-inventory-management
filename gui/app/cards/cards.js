'use strict';

angular.module('main.cards', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/cardDetails/:cardId', {
        templateUrl: 'cards/cardDetails.html',
        controller: 'CardCtrl'
    });
}])

.controller('CardCtrl', ['$scope', '$routeParams', 'CardSvc', function ($scope, $routeParams, cardSvc) {
    $scope.card;
    
    cardSvc.getCardDetails($routeParams.cardId).success(function (data) {
        $scope.card = data;
    });
}])

.factory('CardSvc',['$http', function($http){    
    var cardSvc={};

    cardSvc.getCardDetails = function(cardId){
        return $http.get('http://localhost:8080/cards/' + cardId);
    };

    return cardSvc;
}]);