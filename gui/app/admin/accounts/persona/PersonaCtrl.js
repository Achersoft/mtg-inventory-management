angular.module('main')
    .controller('PersonaCtrl', ['$scope', '$state', 'PersonaState', 'PersonaSvc', 'dialogs',
                '$rootScope', 'RESOURCES',
        function ($scope, $state, personaState, personaSvc, dialogs, $rootScope, RESOURCES) {     
            $scope.personas = personaState.get().personas;
            $scope.persona = personaState.get().persona;
            $scope.personaContext = personaState.get();
            
            $scope.create = function(isValid) {
                if(isValid) {
                    personaSvc.createPersona(personaState.get().persona)
                        .then(function() {
                            $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully created persona'});
                            $state.go("admin.persona.list");
                        });
                }
            };    
            
            $scope.save = function(isValid) {
                if(isValid) {
                    personaSvc.savePersona(personaState.get().persona.id, personaState.get().persona)
                        .then(function(response) {
                            $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully updated persona'});
                            personaState.setPersona(response.data, false);
                        });
                }
            };    
            
            $scope.edit = function() {
                personaState.get().editMode = true;
            }; 
            
            $scope.delete = function(id, name) {
                 dialogs.confirm('Delete Persona "' + name + '"?',
                      'Are you sure you want to delete this persona?',
                      {backdrop : 'static',
                       animation : true,
                       size : 'sm'}).result.then(function() {
                    personaSvc.deletePersona(id)
                        .then(function() {
                            $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully deleted persona'});
                            $state.reload();
                        });
                });
            }; 
        }
    ]);
