angular.module('main')

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/search/results', {
        templateUrl: 'search/searchList.html',
        controller: 'SearchCtrl'
    });
}])

.controller('SearchCtrl', ['$scope', 'NgTableParams', 'SearchSvc', function ($scope, NgTableParams, searchSvc) {
    $scope.tableParams = new NgTableParams({
        page: 1,         
        count: 20     
    },
    {   total: 0, 
        counts: [], 
        getData: function ($defer, params) {
            searchSvc.search().success(function (result) {
                params.total(result.length);
                $defer.resolve(result.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }).error(function(error){
                $scope.status = 'Unable to load list for page ' + params.page() + ': ' + error;
            });
        }
    });
}])

.factory('SearchState', [
    function() {                    
        var searchState = {
            name: null,
            like: null,
            limit: true
        };
        
        function setCardName(cardName) {
            searchState = {name: cardName, limit: false};
        }
        
        function setLikeName(cardName) {
            searchState = {like: cardName, limit: true};
        }

        function setContext(data) {
            searchState = data;
        }

        function get() {
            return searchState;
        }

        return {
            setCardName: setCardName,
            setLikeName: setLikeName,
            setContext: setContext,
            get: get
        };
    }])

.factory('SearchSvc',['$http', 'RESOURCES', 'SearchState', function($http, RESOURCES, searchState) {    
    var searchSvc={};

    searchSvc.search = function(){
        return $http.put(RESOURCES.REST_BASE_URL + '/cards/search/', searchState.get());
    };
    
    return searchSvc;
}]);