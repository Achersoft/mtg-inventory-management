'use strict';

// Declare app level module which depends on views, and components
angular.module('main', [
  'ngRoute',
  'ngTable', 
  'main.sets',
  'main.version'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/view1'});
}]);
