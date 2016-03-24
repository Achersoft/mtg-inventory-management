angular.module('main')
    .factory('CertificationsState', [
        function() {                    
            var certificationState = {
                certification: {},
                certifications: {},
                editMode: false
            };
            
            function setCertifications(data) {
                certificationState.certifications = data;
            }
            
            function setCertification(data, editMode) {
                certificationState.certification = data;
                certificationState.editMode = editMode;
            }

            function get() {
                return certificationState;
            }
           
            return {
                setCertifications: setCertifications,
                setCertification: setCertification,
                get: get
            };
        }]);