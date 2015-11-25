'use strict';

angular.module('main.sets', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/setSelection', {
        templateUrl: 'sets/setSelection.html',
        controller: 'SetCtrl'
    })
    .when('/setList/:setId', {
        templateUrl: 'sets/setList.html',
        controller: 'SetCtrl'
    });
}])

.controller('SetCtrl', ['$scope', '$routeParams', 'NgTableParams', 'SetSvc', function ($scope, $routeParams, NgTableParams, setSvc) {
    $scope.sets;
    
    setSvc.getSets().success(function (data) {
        $scope.sets = data;
    });

    $scope.headerLinks = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];

    $scope.startsWith = function (actual, expected) {
        var lowerStr = (actual + "").toLowerCase();
        return lowerStr.indexOf(expected.toLowerCase()) === 0;
    };

    $scope.gotoAnchor = function(x) {
        var newHash = 'anchor' + x;
        if ($location.hash() !== newHash) {
          // set the $location.hash to `newHash` and
          // $anchorScroll will automatically scroll to it
          $location.hash('anchor' + x);
        } else {
          // call $anchorScroll() explicitly,
          // since $location.hash hasn't changed
          $anchorScroll();
        }
    };

    $scope.tableParams = new NgTableParams({
        page: 1,         
        count: 20     
    },
    {   total: 0, 
        counts: [], 
        getData: function ($defer, params) {
            setSvc.getCards($routeParams.setId).success(function (result) {
                params.total(result.length);
                $defer.resolve(result.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }).error(function(error){
                $scope.status = 'Unable to load candidate list for page ' + params.page() + ': ';
            });
        }
    });
}])

.factory('SetSvc',['$http', function($http){    
    var setSvc={};

    setSvc.getSets = function(){
        return $http.get('http://localhost:8080/cards/sets?language=English');
    };
    
    setSvc.getCards = function(setId){
        return $http.get('http://localhost:8080/cards/sets/' + setId + '?language=English');
    };

    return setSvc;
}]);