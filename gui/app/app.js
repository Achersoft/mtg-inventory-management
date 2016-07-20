'use strict';

// Declare app level module which depends on views, and components
angular.module('main', [
  'ngRoute',
  'ngTable', 
  'ngCart',
  'angular-storage',
  'angular-jwt',
  'http-auth-interceptor',
  'main.auth',
  'main.users',
  'main.home',
  'main.storage',
  'main.cards',
  'main.import',
  'main.inventory',
  'main.checkout'
])
.constant('RESOURCES', (function() {
    'use strict';
    var restBaseUrl = 'http://localhost:8080/rest/mtg';
    var uriBaseUrl = 'http://localhost:8080';
    return {
        // Service URLs
        IMG_BASE_URL    : uriBaseUrl,
        REST_BASE_URL   : restBaseUrl,
        ENUMS_BASE_URL  : restBaseUrl + '/enum',
        LOGIN_URL       : restBaseUrl + '/login',
        LOGOUT_URL      : restBaseUrl + '/logout'
    };
})())        
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/setSelection/English'});
}])
.run(['$rootScope', '$location', 'RESOURCES', function($rootScope, $location, RESOURCES) {

    // If angular-http-auth inteceptor fires "requires login" event, we need to display login "view"
    $rootScope.$on('event:auth-loginRequired', function() {
        $rootScope.loginRequired = true;
        $location.path("/setSelection/English");
    });
    
    $rootScope.$on('event:auth-loginConfirmed', function() {
        $rootScope.loginRequired = false;
    });
    
    $rootScope.$on('event:auth-forbidden', function(event, rejection) {
        console.log(rejection);
       // errorDialogSvc.showHttpError('Unauthorized Request',
        //                             'You are not authorized for this operation.',
        //                             rejection);
    });

    $rootScope.$on(RESOURCES.ESI_EVENT_GEN_ERROR, function(event, message) {
        //errorDialogSvc.showError('Application Error Occurred',
        //                         message);
    });

    // Handles HTTP errors that are not specifically handled by the application services/controllers
    $rootScope.$on(RESOURCES.ESI_EVENT_HTTP_ERROR, function(event, rejection) {
        // 401 - Not Auth is handled separately - causing app to display login "page" (area)
        // 403 - is Forbidden and is handled by event above - which is broadcast by
        // http-auth-inteceptor
        if ( rejection.status !== 401 && rejection.status !== 403 ) {
            var errstr;
            if ( rejection.status === -1 ) {
                errstr = 'Unable to communicate with server.';
            } else {
                errstr = 'An unexpected error occurred while comunicating with the server.';
            }
            //errorDialogSvc.showHttpError('Server Error', errstr, rejection);
        }
    });
}]);
