'use strict';

angular.module('main.import', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/import/allSets', {
        templateUrl: 'import/allSets.html',
        controller: 'ImportCtrl'
    });
}])

.controller('ImportCtrl', ['$scope', '$routeParams', 'RESOURCES', 'ImportSvc', function ($scope, $routeParams, RESOURCES, importSvc) {
    $scope.restBaseURL = RESOURCES.REST_BASE_URL;
}])

.factory('ImportSvc',['$http', function($http){    
    var importSvc={};

    return importSvc;
}]);