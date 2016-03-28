'use strict';

// Declare app level module which depends on views, and components
angular.module('main', [
  'ngRoute',
  'ngTable', 
  'ngCart',
  'main.users',
  'main.home',
  'main.sets',
  'main.cards',
  'main.import',
  'main.checkout'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/'});
}]);
