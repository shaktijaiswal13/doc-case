'use strict';

angular.module('myApp.DocumentCreate', ['ngRoute'])

.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.when('/documentcreate', {
            templateUrl: 'js/views/DocumentCreate/DocumentCreateView.html',
            controller: 'documentCreateCtrl'
        });
    }
])


.controller('documentCreateCtrl', ['$scope', '$http',
    function($scope, $http) {
        $scope.saveDocument = function() {
            $http.post("./rest/document")
                .success(function() {
                    $scope.showErrorMessage = false
                    $scope.showSuccessMessage = true
                }).error(function(err) {
                    $scope.showSuccessMessage = false
                    $scope.showErrorMessage = true
                    $scope.errorMessage = err
                    console.log("error...." + err);
                });
        }
    }
]);