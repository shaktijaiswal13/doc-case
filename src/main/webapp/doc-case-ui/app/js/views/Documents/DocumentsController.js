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
    $http.get("./rest/documents")
        .success(function(response) {
            $scope.documents = response;
            for (var i = 0; i < $scope.documents.length; i++) {
                var doc = $scope.documents[i]
                doc.url = "." + encodeURI(doc.url) + "/inline"
            }
        });
    $scope.deleteDocument = function(document) {
        var url = ".rest/documents/" + encodeURIComponent(document.id);
        $http.delete(url);

    };
});