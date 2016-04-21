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
    var restBaseUrl = 'http://localhost:8080';
    var uiBaseUrl   = 'http://localhost:8080';

    // Determine base URL for UI server requests...
    var initInjector = angular.injector(['ng']);
    var $window = initInjector.get('$window');
    uiBaseUrl = $window.location.origin + $window.location.pathname;

    var esiRequiredSkills = 'esiRequiredSkills';
    var esiDesiredSkills  = 'esiDesiredSkills';

    return {
        // Service URLs
        REST_BASE_URL   : restBaseUrl,
        ENUMS_BASE_URL  : restBaseUrl + '/enum',
        LOGIN_URL       : restBaseUrl + '/login',
        LOGOUT_URL      : restBaseUrl + '/logout',
        CANDIDATES_URL  : restBaseUrl + '/candidates',
        CANDIDATE_REINDEX_URL : restBaseUrl + '/admin/search/index/build',
        CONTRACTS_URL  : restBaseUrl + '/contracts',
        POSITIONS_URL   : restBaseUrl + '/positions',
        KEYWORDS_URL   : restBaseUrl + '/keywords',

        // UI URLs
        BOOTSTRAP_CSS_URL : uiBaseUrl + '/components/bootstrap/dist/css/bootstrap.css',
        ESI_CSS_URL       : uiBaseUrl + '/css/esi-default.css',

        // Event IDs
        ESI_EVENT_HTTP_ERROR : 'esiEventHttpError',
        ESI_EVENT_GEN_ERROR  : 'esiEventGenError',

        STYLE_CLASS_REQUIRED_SKILL : esiRequiredSkills,
        STYLE_CLASS_DESIRED_SKILL : esiDesiredSkills,
        ESI_REQUIRED_SKILL_CSS_QP : 'requiredClass=' + esiRequiredSkills, 
        ESI_SKILLS_CSS_QP : 'highlightClass=' + esiRequiredSkills,
        ESI_DESIRED_SKILL_CSS_QP  : 'desiredClass=' + esiDesiredSkills, 

        LCL_CACHE_DFLT_MAX_TIME_TO_LIVE  : 1800000,  // default - 1/2 hour - in millis
        ENUM_CACHE_EXPIRY_TIME_IN_MILLIS : 600000,    // 10 mins - in millis

        INFO_MSG_EVENT : 'infoMsgEvent',

        CLEARANCE_STATUSES: ['not started',
            'applicant working on paperwork',
            'in Q for 1st poly',
            '1st poly taken',
            '2nd poly taken',
            '3rd poly taken',
            'poly successful - case in eval',
            'granted']

    };
})())        
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/'});
}])
.run(['$rootScope', 'RESOURCES', function($rootScope, RESOURCES) {

    // If angular-http-auth inteceptor fires "requires login" event, we need to display login "view"
    $rootScope.$on('event:auth-loginRequired', function() {
        $rootScope.loginRequired = true;
    });
    
    $rootScope.$on('event:auth-loginConfirmed', function() {
        $rootScope.loginRequired = false;
    });
    
    $rootScope.$on('event:auth-forbidden', function(event, rejection) {
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
