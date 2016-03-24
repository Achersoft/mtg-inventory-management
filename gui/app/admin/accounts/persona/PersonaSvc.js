angular.module('main')
    .factory('PersonaSvc',['$http', '$q', 'ErrorDialogSvc', 'RESOURCES',
                           function($http, $q, errorDialogSvc, RESOURCES){
        
        var personaSvc={};

        personaSvc.getPersonas = function(){
            return $http.get(RESOURCES.REST_BASE_URL + "/admin/personas",
                             {silentHttpErrors : true})
                        .catch(function(rejection) {
                            errorDialogSvc.showHttpError('Unable to Retrieve Personas',
                                'Failed to retrieve list of existing Personas.',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        personaSvc.getPersona = function(link){
            return $http.get(RESOURCES.REST_BASE_URL + link,
                             {silentHttpErrors : true})
                        .catch(function(rejection) {
                            errorDialogSvc.showHttpError('Unable to Retrieve Persona',
                                'Failed to retrieve selected Persona.',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        personaSvc.createPersona = function(persona){
           return $http.post(RESOURCES.REST_BASE_URL + "/admin/personas/create",
                             persona,{silentHttpErrors : true})
                        .catch(function(rejection) {
                            errorDialogSvc.showHttpError('Unable to Create Persona',
                                'Failed to add Persona "' + persona.name + '".',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        personaSvc.savePersona = function(id, persona){
           return $http.put(RESOURCES.REST_BASE_URL + "/admin/personas/" + id,
                            persona, {silentHttpErrors : true})
                       .catch(function(rejection) {
                           errorDialogSvc.showHttpError('Unable to Save Persona',
                                'Failed to save Persona "' + persona.name + '".',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
          
        personaSvc.deletePersona = function(id){
           return $http.delete(RESOURCES.REST_BASE_URL + "/admin/personas/" + id,
                               {silentHttpErrors : true})
                       .catch(function(rejection) {
                           errorDialogSvc.showHttpError('Unable to Delete Persona',
                                'Failed to delete selected Persona.',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        return personaSvc;
    }]);