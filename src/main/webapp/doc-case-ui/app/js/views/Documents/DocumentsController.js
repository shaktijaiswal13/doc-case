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

            for (var i = 0; i < $scope.documents.length; i++) {
                var doc = $scope.documents[i]
                doc.imageURI = $scope.getImageURI(doc._id)
            }
        });
    $scope.getImageURI = function(id) {
        return "./rest/documents/retrieve/" + encodeURIComponent(id)
    };
});