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

.controller('UserCtrl', ['$scope', '$routeParams', 'NgTableParams', 'UserSvc', 'UserState', function ($scope, $routeParams, NgTableParams, userSvc, userState) {
    $scope.userContext = userState.get();
    
    $scope.edit = function() {
        userState.get().editMode = true;
    }; 
    
    $scope.create = function(isValid) {
        userSvc.createUser($scope.userContext.user);
    }; 
    
    $scope.delete = function(userId) {
        userSvc.deleteUser(userId).then(function() {
            location.reload();
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