angular.module('main').directive('navBar',[ 'NavBarSvc', function(navBarSvc){
    return{
        restrict:'E',
        templateUrl:'common/topNav/navBar.html',
        bindToController: true,
        controllerAs: 'topNavCtrl',
        controller: function () {
            this.selectedCard = '';
            
            this.states = ["Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois"];
	
            
            this.onSelect = function($item){
                this.selectedCard = '';
            };
            
            this.searchForCard = function(viewValue) {
                console.log('asdfasdfdsf'+viewValue);
                return navBarSvc.search(viewValue).then(function(response) {
                    return response.data;
                });
            };
        }
    };
}])

.factory('NavBarSvc',['$http', 'RESOURCES', function($http, RESOURCES){    
    var navBarSvc={};

    navBarSvc.search = function(name){
        return $http.get(RESOURCES.REST_BASE_URL + '/search/?name=' + name);
    };
    
    return navBarSvc;
}]);