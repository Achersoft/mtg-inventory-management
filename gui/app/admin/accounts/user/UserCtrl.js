angular.module('main')
    .controller('UserCtrl', ['$scope', '$state', 'UserState', 'UserSvc', 'dialogs',
                '$rootScope', 'RESOURCES',
        function ($scope, $state, userState, userSvc, dialogs, $rootScope, RESOURCES) {     
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
    ]);
