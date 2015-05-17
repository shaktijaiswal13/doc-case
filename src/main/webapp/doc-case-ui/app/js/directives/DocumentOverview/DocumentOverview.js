angular.module('myApp.directives', [])
    .directive('documentoverview', ['$parse',
        function($parse) {
            return {
                restrict: 'E',
                replace: true,
                scope: {
                    model: "="
                },
                templateUrl: 'js/directives/DocumentOverview/DocumentOverview.html',
                controller: function($scope, $http, $attrs) {
                    //                    $scope.document = $parse($attrs.model);
                   // console.log($scope.model);
                }
            }
        }
    ]);