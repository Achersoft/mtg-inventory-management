angular.module('main')
    .factory('UserState', [
        function() {                    
            var userState = {
                user: {},
                users: {},
                editMode: false
            };
            
            function setUsers(data) {
                userState.users = data;
            }
            
            function setUser(data, editMode) {
                userState.user = data;
                userState.editMode = editMode;
            }

            function get() {
                return userState;
            }
           
            return {
                setUsers: setUsers,
                setUser: setUser,
                get: get
            };
        }]);