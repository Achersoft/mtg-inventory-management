angular.module('main')
    .factory('ReindexSvc',['$http', 'RESOURCES', function($http, RESOURCES){
        
        var reindexSvc={};

        reindexSvc.reindexCandidates = function(){
            return $http.post(RESOURCES.CANDIDATE_REINDEX_URL, {});
        };
                
        return reindexSvc;
    }]);