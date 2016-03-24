angular.module('main').directive('personaSelection',['esiEnumValueFilterFilter',function(enumFilter){
    return{
        restrict:'E',
        scope:{
            model:'=',
            editMode: '=',
            selectNgOptions: '='
        },
        link: function(scope) {
            scope.getText = function(limit, list, selectNgOptions) {
                var text = "";
                var isFirst = true;
                if (list) {
                    for (var i = 0; i < list.length; i++) {
                        if (!isFirst) {
                            text = text + ", ";
                        }
                        text = text + enumFilter(list[i].id, selectNgOptions);
                        isFirst = false;
                        //limit items
                        if (limit && i === 8) {
                            return text + "...";
                        }
                    }
                }
                return text ? text : "None selected";
            };            
        },
        templateUrl:'admin/accounts/user/common/personaSelectionTemplate.html'
    };
}]);