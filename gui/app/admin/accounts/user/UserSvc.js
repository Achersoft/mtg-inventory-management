angular.module('main')
    .factory('UserSvc',['$http', '$q', 'ErrorDialogSvc', 'RESOURCES',
                        function($http, $q, errorDialogSvc, RESOURCES){
        
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