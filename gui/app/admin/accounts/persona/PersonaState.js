angular.module('main')
    .factory('PersonaState', [
        function() {                    
            var personaState = {
                persona: {},
                personas: {},
                editMode: false
            };
            
            function setPersonas(data) {
                personaState.personas = data;
            }
            
            function setPersona(data, editMode) {
                personaState.persona = data;
                personaState.editMode = editMode;
            }

            function get() {
                return personaState;
            }
           
            return {
                setPersonas: setPersonas,
                setPersona: setPersona,
                get: get
            };
        }]);