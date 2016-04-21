angular.module('main')

.config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when('/login/', {
            templateUrl: 'login/login.html',
            controller: 'LoginCtrl'
        });
}])
.controller('LoginCtrl',['$scope', 'AuthorizationSvc', 'AuthorizationState',
    function($scope, AuthorizationSvc, AuthorizationState){
        $scope.loginPrompt = { username : '',
                               password : '',
                               invalidMsg : ''};
        
        $scope.login = function(){
            AuthorizationSvc.authenticate($scope.loginPrompt.username, $scope.loginPrompt.password)
                    .then(function(response) {
                            //console.log("LOGGED IN SUCCESSFULLY!");
                        },
                          function(response) {
                            // Wipe out password to allow re-entry
                            $scope.loginPrompt.username = '';
                            $scope.loginPrompt.password = '';
                            $scope.loginPrompt.invalidMsg = "Login failed. Invalid name and/or password. Please retry.";
                        })
                    .finally(function() {
                        // Wipe out - if error 
                        $scope.loginPrompt.username = '';
                        $scope.loginPrompt.password = '';
                        // Reset form - to hide validation errors, etc. for next go round...
                        $scope.estaffLoginForm.$setPristine();
                    });
        };
        
        $scope.logout = function() {
            AuthorizationSvc.release()
                    .then(function(response) {
                            //console.log("LOGGED OUT SUCCESSFULLY!");
                            $state.go('logout');
                        });
        };
        
        $scope.isLoggedIn = function() {
            return AuthorizationState.isAuthorized();
        };
    }
]);


