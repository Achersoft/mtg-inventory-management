angular.module('main')
    .controller('ReindexCtrl', ['$scope', 'ReindexSvc',
        function ($scope, reindexSvc) {
            
            $scope.reindexCandidates = function() {
                reindexSvc.reindexCandidates();
            };
        }]);