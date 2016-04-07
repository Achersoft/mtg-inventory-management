'use strict';

angular.module('main.users', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/users/viewAll', {
        templateUrl: 'admin/user/list/userList.html',
        controller: 'UserCtrl'
    })
    .when('/users/add', {
        templateUrl: 'admin/user/detail/create/userCreate.html',
        controller: 'UserCtrl'
    });
}])

.controller('UserCtrl', ['$scope', '$location', '$route', '$routeParams', 'NgTableParams', 'UserSvc', 'UserState', function ($scope, $location, $route, $routeParams, NgTableParams, userSvc, userState) {
    $scope.userContext = userState.get();
    
    
    $scope.edit = function(userId) {
        $scope.userContext.editMode = true;
        $scope.userContext.user = userSvc.getUser(userId);
        $location.path("/users/add");
    }; 
    
    $scope.create = function(isValid) {
        userSvc.createUser($scope.userContext.user).then(function() {
            $location.path("/users/viewAll");
        });
    }; 
    
    $scope.delete = function(userId) {
        userSvc.deleteUser(userId).then(function() {
            $route.reload();
        });
    }; 
    
    $scope.tableParams = new NgTableParams({
        page: 1,         
        count: 20     
    },
    {   total: 0, 
        counts: [], 
        getData: function ($defer, params) {
            userSvc.getUsers().success(function (result) {
                params.total(result.length);
                $defer.resolve(result.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }).error(function(error){
                $scope.status = 'Unable to load candidate list for page ' + params.page() + ': ';
            });
        }
    });
}])

.factory('UserState', [
    function() {                    
        var userState = {
            user: {},
            users: {},
            editMode: false
        };

        function setUsers(data) {
            userState.users = data;
        }

        function setUser(data, editMode) {
            userState.user = data;
            userState.editMode = editMode;
        }

        function get() {
            return userState;
        }

        return {
            setUsers: setUsers,
            setUser: setUser,
            get: get
        };
    }])

.factory('UserSvc',['$http', function($http){    
    var userSvc={};

    userSvc.getUsers = function(){
        return $http.get('http://localhost:8080/users/');
    };
    
    userSvc.getUser = function(userId){
        return $http.get('http://localhost:8080/users/' + userId);
    };
    
    userSvc.createUser = function(user){
        return $http.post("http://localhost:8080/users/create",
                          user, {silentHttpErrors : true})
                     .catch(function(rejection) {
                        /* errorDialogSvc.showHttpError('Unable to Create User',
                             'Failed to add User "' + user.username + '".',
                             rejection);
                         return $q.reject(rejection);*/
                     });
    };
     
    userSvc.deleteUser = function(id){
        return $http.delete("http://localhost:8080/users/" + id,
                            {silentHttpErrors : true})
                    .catch(function(rejection) {

                     });
    };
        
    return userSvc;
}]);