angular.module('main')
    .factory('AuthenticatorState', [
        function() {                    
            var authenticatorState = {
                authenticator: {},
                authenticators: {},
                editMode: false
            };
            
            function setAuthenticators(data) {
                authenticatorState.authenticators = data;
            }
            
            function setAuthenticator(data, editMode) {
                authenticatorState.authenticator = data;
                authenticatorState.editMode = editMode;
            }

            function get() {
                return authenticatorState;
            }
           
            return {
                setAuthenticators: setAuthenticators,
                setAuthenticator: setAuthenticator,
                get: get
            };
        }]);