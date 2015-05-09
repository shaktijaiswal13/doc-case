'use strict';

angular.module('myApp.Documents', ['ngRoute'])

.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.when('/documents', {
            templateUrl: 'js/views/Documents/DocumentsView.html',
            controller: 'documentsCtrl'
        });
    }
])

.controller('documentsCtrl', function($scope, $http) {
    $http.get("./rest/documents/retrieve")
        .success(function(response) {
            $scope.documents = response;
        });
});