angular.module('main')
    .factory('CertificationsSvc',['$http', '$q', 'ErrorDialogSvc', 'RESOURCES',
                                 function($http, $q, errorDialogSvc, RESOURCES){
        
        var certificationsSvc={};

        certificationsSvc.getCertifications = function(){
            return $http.get(RESOURCES.REST_BASE_URL + "/admin/certifications",
                             {silentHttpErrors : true})
                        .catch(function(rejection) {
                            errorDialogSvc.showHttpError('Unable to Retrieve Certifications',
                                'Failed to retrieve list of existing Certifications.',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        certificationsSvc.createCertification = function(certification){
           return $http.post(RESOURCES.REST_BASE_URL + "/admin/certifications/add",
                             certification,{silentHttpErrors : true})
                        .catch(function(rejection) {
                            errorDialogSvc.showHttpError('Unable to Create Certification',
                                'Failed to add Certification "' + certification.name + '".',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        certificationsSvc.saveCertification = function(id, certification){
           return $http.put(RESOURCES.REST_BASE_URL + "/admin/certifications/" + id,
                            certification, {silentHttpErrors : true})
                       .catch(function(rejection) {
                           errorDialogSvc.showHttpError('Unable to Save Certification',
                                'Failed to update Certification "' + certification.name + '".',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
          
        certificationsSvc.deleteCertification = function(id){
           return $http.delete(RESOURCES.REST_BASE_URL + "/admin/certifications/" + id,
                               {silentHttpErrors : true})
                       .catch(function(rejection) {
                           errorDialogSvc.showHttpError('Unable to Delete Certification',
                                'Failed to delete selected Certification.',
                                rejection);
                            return $q.reject(rejection);
                        });
        };
        
        return certificationsSvc;
    }]);