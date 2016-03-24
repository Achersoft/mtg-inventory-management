angular.module('main')
    .factory('AuthenticatorSvc',['$http', '$q', 'ErrorDialogSvc', 'RESOURCES',
                                 function($http, $q, errorDialogSvc, RESOURCES){
        
        var authenticatorSvc={};

        authenticatorSvc.getAuthenticators = function(){
            return $http.get(RESOURCES.REST_BASE_URL + "/admin/authenticators",
                             {silentHttpErrors : true})
                        .catch(function(rejection) {
                            errorDialogSvc.showHttpError('Unable to Retrieve Authenticators',
                                'Failed to retrieve list of existing Authenticators.',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        authenticatorSvc.getAuthenticator = function(link){
            return $http.get(RESOURCES.REST_BASE_URL + link, {silentHttpErrors : true})
                        .catch(function(rejection) {
                            errorDialogSvc.showHttpError('Unable to Retrieve Authenticator',
                                'Failed to retrieve selected Authenticator.',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        authenticatorSvc.createAuthenticator = function(authenticator){
           return $http.post(RESOURCES.REST_BASE_URL + "/admin/authenticators/add",
                             authenticator,{silentHttpErrors : true})
                        .catch(function(rejection) {
                            errorDialogSvc.showHttpError('Unable to Create Authenticator',
                                'Failed to add Authenticator "' + authenticator.name + '".',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        authenticatorSvc.saveAuthenticator = function(id, authenticator){
           return $http.put(RESOURCES.REST_BASE_URL + "/admin/authenticators/" + id,
                            authenticator, {silentHttpErrors : true})
                       .catch(function(rejection) {
                           errorDialogSvc.showHttpError('Unable to Save Authenticator',
                                'Failed to update Authenticator "' + authenticator.name + '".',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
          
        authenticatorSvc.deleteAuthenticator = function(id){
           return $http.delete(RESOURCES.REST_BASE_URL + "/admin/authenticators/" + id,
                               {silentHttpErrors : true})
                       .catch(function(rejection) {
                           errorDialogSvc.showHttpError('Unable to Delete Authenticator',
                                'Failed to delete selected Authenticator.',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        return authenticatorSvc;
    }]);