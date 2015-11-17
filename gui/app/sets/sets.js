'use strict';

angular.module('main.sets', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/setSelection', {
        templateUrl: 'sets/setSelection.html',
        controller: 'SetCtrl'
    })
    .when('/setList', {
        templateUrl: 'sets/setList.html',
        controller: 'SetCtrl'
    });
}])

.controller('SetCtrl', ['$scope', 'NgTableParams', 'SetSvc', function ($scope, NgTableParams, setSvc) {
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
        // Initial settings, when list first displayed
        page: 1,            // show first page
        count: 20          // count per page
    },
    {   total: 0, // length of data - will update via getData()
        counts: [], // empty to disable this -- [10, 20, 50, 100],
                    // Items per page toggler.
        // ngTable > v1.0.0 - expects getData to return Array of data itself or a promise 
        // that resolves to the Array of data (we return internal data directly below)
        getData: function ($defer, params) {
            setSvc.getCards().success(function (result) {
                $defer.resolve(result);
            }).error(function(error){
                $scope.status = 'Unable to load candidate list for page ' + params.page() + ': ';
            });
        }
    });
}])

.factory('SetSvc',['$http', function($http){    
    var setSvc={};

    setSvc.getSets = function(){
        return $http.get('http://localhost:8080/cards/sets');
    };
    
    setSvc.getCards = function(){
        return $http.get('http://localhost:8080/cards');
     //   return [{"layout":"normal","type":"Creature â€” Dragon","types":["Creature"],"colors":["Red", "Blue"],"name":"Nalathni Dragon","rarity":"Special","subtypes":["Dragon"],"power":"1","toughness":"1","cmc":4,"manaCost":[{"cost":"2"},{"cost":"r"},{"cost":"r"}],"text":"Flying; banding (Any creatures with banding, and up to one without, can attack in a band. Bands are blocked as a group. If any creatures with banding you control are blocking or being blocked by a creature, you divide that creature's combat damage, not its controller, among any of the creatures it's being blocked by or is blocking.)\n{R}: Nalathni Dragon gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Nalathni Dragon at the beginning of the next end step.","flavor":"These small but intelligent Dragons and their Olesian allies held back the tide of Pashalik Mons's hordes of Goblin Raiders.\nDragonCon 1994","artist":"Michael Whelan","number":"1","imageName":"nalathni dragon","multiverseid":97050,"id":"2f6ad2b7730247c9eb7acc83abc33bb6fe569749"}];
    };

    return setSvc;
}]);