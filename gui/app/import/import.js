'use strict';

angular.module('main.import', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/import/allSets', {
        templateUrl: 'import/allSets.html',
        controller: 'ImportCtrl'
    });
}])

.controller('ImportCtrl', ['$scope', '$routeParams', 'ImportSvc', function ($scope, $routeParams, importSvc) {

}])

.factory('ImportSvc',['$http', function($http){    
    var importSvc={};

    return importSvc;
}]);