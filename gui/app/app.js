'use strict';

// Declare app level module which depends on views, and components
angular.module('main', [
  'ngRoute',
  'ngTable', 
  'ngCart',
  'main.home',
  'main.sets',
  'main.cards',
  'main.import',
  'main.checkout',
  'main.version'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/'});
}]);
