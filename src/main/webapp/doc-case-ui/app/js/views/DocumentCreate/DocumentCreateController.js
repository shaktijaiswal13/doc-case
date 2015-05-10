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


.controller('documentCreateCtrl', ['$scope', '$http', 'fileUpload',
    function($scope, $http, fileUpload) {
        $scope.saveDocument = function() {
            uploadFile();
        };
        $scope.uploadFile = function() {
            var file = $scope.myFile;
            console.log(file) //{webkitRelativePath: "", lastModified: 1430866129000, lastModifiedDate: Tue May 05 2015 23:48:49 GMT+0100 (IST), name: "Page 1&2.png", type: "image/png"…}
            console.log('file is ' + JSON.stringify(file));
            var uploadUrl = "./rest/file";
            fileUpload.uploadFileToUrl(file, uploadUrl);
        };
    }
])


.directive('fileModel', ['$parse',
    function($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function() {
                    scope.$apply(function() {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }
])

.service('fileUpload', ['$http',
    function($http) {
        this.uploadFileToUrl = function(file, uploadUrl) {
            var fd = new FormData();
            fd.append('file', file);
            $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            }).success(function() {
                console.log("File saved successfully....");
                console.log("Saving document....");
                var data = {
                    name: "Electricity bill"
                }
                $http.post("./rest/document", data)
                    .success(function() {
                        console.log("Document saved successfully....");
                    }).error(function(err) {
                        console.log("error in saving document...." + err);
                    });
            }).error(function(err) {
                console.log("error in saving file...." + err);
            });
        }
    }
]);