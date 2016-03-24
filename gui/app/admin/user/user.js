'use strict';

angular.module('main.users', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/users/viewAll', {
        templateUrl: 'admin/user/list/userList.html',
        controller: 'UserCtrl'
    });
}])

.controller('UserCtrl', ['$scope', '$state', 'UserState', 'UserSvc', 'dialogs',
                '$rootScope', 'RESOURCES', function ($scope, $state, userState, userSvc, dialogs, $rootScope, RESOURCES) {     
    $scope.userContext = userState.get();

    $scope.create = function(isValid) {
        if(isValid) {
            userSvc.createUser(userState.get().user)
                .then(function() {
                    $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully created user'});
                    $state.go("admin.user.list");
                });
        }
    };    

    $scope.save = function(isValid) {
        console.log(isValid);
        if(isValid) {
            userSvc.saveUser(userState.get().user.id, userState.get().user)
                .then(function(response) {
                    $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully updated user'});
                    userState.setUser(response.data, false);
                });
        }
    };    

    $scope.edit = function() {
        userState.get().editMode = true;
    }; 

    $scope.delete = function(id, name) {
         dialogs.confirm('Delete User "' + name + '"?',
              'Are you sure you want to delete this user?',
              {backdrop : 'static',
               animation : true,
               size : 'sm'}).result.then(function() {
            userSvc.deleteUser(id)
                .then(function() {
                    $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully deleted user'});
                    $state.reload();
                });
        });
    }; 
}
])

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

.factory('UserSvc',['$http', '$q', 'ErrorDialogSvc', 'RESOURCES', function($http, $q, errorDialogSvc, RESOURCES){
    var userSvc={};

    userSvc.getUsers = function(){
        return $http.get(RESOURCES.REST_BASE_URL + "/admin/users",
                         {silentHttpErrors : true})
                    .catch(function(rejection) {
                        errorDialogSvc.showHttpError('Unable to Retrieve Users',
                            'Failed to retrieve list of existing Users.',
                            rejection);
                        return $q.reject(rejection);
                    });
    };

    userSvc.getUser = function(link){
        return $http.get(RESOURCES.REST_BASE_URL + link,
                         {silentHttpErrors : true})
                    .catch(function(rejection) {
                        errorDialogSvc.showHttpError('Unable to Retrieve User',
                            'Failed to retrieve selected User.',
                            rejection);
                        return $q.reject(rejection);
                    });
    };

    userSvc.createUser = function(user){
       return $http.post(RESOURCES.REST_BASE_URL + "/admin/users/create",
                         user, {silentHttpErrors : true})
                    .catch(function(rejection) {
                        errorDialogSvc.showHttpError('Unable to Create User',
                            'Failed to add User "' + user.username + '".',
                            rejection);
                        return $q.reject(rejection);
                    });
    };

    userSvc.saveUser = function(id, user){
       return $http.put(RESOURCES.REST_BASE_URL + "/admin/users/" + id,
                        user, {silentHttpErrors : true})
                   .catch(function(rejection) {
                        errorDialogSvc.showHttpError('Unable to Save User',
                            'Failed to update User entry for "' + user.username + '".',
                            rejection);
                        return $q.reject(rejection);
                    });
    };

    userSvc.deleteUser = function(id){
       return $http.delete(RESOURCES.REST_BASE_URL + "/admin/users/" + id,
                           {silentHttpErrors : true})
                   .catch(function(rejection) {
                       errorDialogSvc.showHttpError('Unable to Delete User',
                            'Failed to delete selected User.',
                            rejection);
                        return $q.reject(rejection);
                    });
    };

    return userSvc;
}]);