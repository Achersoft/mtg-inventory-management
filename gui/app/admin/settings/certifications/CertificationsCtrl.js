 angular.module('main')
    .controller('CertificationsCtrl', ['$scope', '$state', 'CertificationsState', 'CertificationsSvc',
                                      'dialogs', '$rootScope', 'RESOURCES',
        function ($scope, $state, certificationsState, certificationsSvc, dialogs, $rootScope, RESOURCES) {     
            $scope.certificationsContext = certificationsState.get();
            
            $scope.create = function(isValid) {
                if(isValid) {
                    certificationsSvc.createCertification(certificationsState.get().certification)
                        .then(function() {
                            $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully created certification'});
                            $state.go("admin.certifications.list");
                        });
                }
            };    
            
            $scope.save = function(isValid) {
                if(isValid) {
                    certificationsSvc.saveCertifications(certificationsState.get().certification.id, certificationsState.get().certifications )
                        .then(function() {
                            $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully updated certification'});
                            $state.go("admin.certifications.list");
                        });
                }
            };    
            
            $scope.edit = function() {
                if (!certificationsState.get().editMode === true)
                    certificationsState.get().editMode = true;
                else
                    certificationsState.get().editMode = false;
            }; 
            
            $scope.delete = function(id, name) {
                dialogs.confirm('Delete certification "' + name + '"?',
                      'Are you sure you want to delete this certification?',
                      {backdrop : 'static',
                       animation : true,
                       size : 'sm'}).result.then(function() {
                     certificationsSvc.deleteCertification(id)
                        .then(function() {
                            $rootScope.$broadcast(RESOURCES.INFO_MSG_EVENT, {msg: 'Successfully deleted certification'});
                            $state.reload();
                        });
                });
            }; 
        }
    ]);
