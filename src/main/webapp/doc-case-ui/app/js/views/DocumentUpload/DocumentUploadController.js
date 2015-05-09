'use strict';

angular.module('myApp.DocumentUpload', ['ngRoute'])

.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.when('/documentupload', {
            templateUrl: 'js/views/DocumentUpload/DocumentUploadView.html',
            controller: 'documentUploadCtrl'
        });
    }
])

.controller('documentUploadCtrl', ['$scope', 'fileUpload',
    function($scope, fileUpload) {

        $scope.uploadFile = function() {
            var file = $scope.myFile;
            console.log(file) //{webkitRelativePath: "", lastModified: 1430866129000, lastModifiedDate: Tue May 05 2015 23:48:49 GMT+0100 (IST), name: "Page 1&2.png", type: "image/png"…}
            console.log('file is ' + JSON.stringify(file));
            var uploadUrl = "./rest/documents/upload";
            fileUpload.uploadFileToUrl(file, uploadUrl);
        };
        $scope.name = 'DrivingLicenseCopy';
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
                console.log("success....");
            }).error(function(err) {
                console.log("error...." + err);
            });
        }
    }
]);