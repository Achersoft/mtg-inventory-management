angular.module('main').directive('navBar',[ 'SearchSvc', 'SearchState', '$location', '$route', function(searchSvc, searchState, $location, $route){
    return{
        restrict:'E',
        templateUrl:'common/topNav/navBar.html',
        bindToController: true,
        controllerAs: 'topNavCtrl',
        controller: function () {
            this.selectedCard = '';
            
            this.onSelect = function($item){
                this.selectedCard = '';
                searchState.setCardName($item.name);
                $location.path("/search/results");
                $route.reload();
            };
            
            this.searchForCard = function(viewValue) {
                searchState.setLikeName(viewValue);
                return searchSvc.search().then(function(response) {
                    return response.data;
                });
            };
        }
    };
}]);