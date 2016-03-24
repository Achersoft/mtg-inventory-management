angular.module('main')
    .controller('AuthenticatorCtrl', ['$scope', '$state', 'AuthenticatorState', 'AuthenticatorSvc',
                                      'dialogs', '$rootScope', 'RESOURCES',
        function ($scope, $state, authenticatorState, authenticatorSvc, dialogs, $rootScope, RESOURCES) {     
            $scope.authenticatorContext = authenticatorState.get();
            
            $scope.create = function(isValid) {
                if(isValid) {
                    authenticatorSvc.createAuthenticator(authenticatorState.get().authenticator)
                        .then(function() {
                            $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully created authenticator'});
                            $state.go("admin.authenticator.list");
                        });
                }
            };    
            
            $scope.save = function(isValid) {
                if(isValid) {
                    authenticatorSvc.saveAuthenticator(authenticatorState.get().authenticator.id, authenticatorState.get().authenticator)
                        .then(function() {
                            $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully updated authenticator'});
                            $state.go("admin.authenticator.list");
                        });
                }
            };    
            
            $scope.edit = function() {
                authenticatorState.get().editMode = true;
            }; 
            
            $scope.delete = function(id, name) {
                dialogs.confirm('Delete Authenticator "' + name + '"?',
                      'Are you sure you want to delete this authenticator?',
                      {backdrop : 'static',
                       animation : true,
                       size : 'sm'}).result.then(function() {
                    authenticatorSvc.deleteAuthenticator(id)
                        .then(function() {
                            $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully deleted authenticator'});
                            $state.reload();
                        });
                });
            }; 
        }
    ]);
