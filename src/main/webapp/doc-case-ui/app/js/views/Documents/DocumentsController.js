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
        }).error(function(err) {
            $scope.showErrorMessage = true;
            $scope.errorMessage = "Error in getting documents...." + err;
            console.log($scope.errorMessage);
        });
    $scope.deleteDocument = function(document) {
        var url = "rest/documents/" + encodeURIComponent(document.id);
        document.temp = {};
        $http.delete(url).success(function(result) {
            document.temp.showSuccessMessage = true;
            document.temp.showErrorMessage = false;
            console.log("File deleted successfully...." + result);
        }).error(function(err) {
            document.temp.showErrorMessage = true;
            document.temp.showSuccessMessage = false;
            document.temp.errorMessage = "Error in deleting file: " + err;
            console.log(document.temp.errorMessage);
        });

    };
});