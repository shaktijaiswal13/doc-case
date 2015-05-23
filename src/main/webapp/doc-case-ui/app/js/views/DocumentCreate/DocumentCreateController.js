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
        $scope.model = {
            document: {},
            temp: {
                showSuccessMessage: false
            }
        }
        $('#labels').tagsinput({
            tagClass: function(item) {
                switch (item) {
                    case 'Europe':
                        return 'label label-primary';
                    case 'America':
                        return 'label label-danger label-important';
                    case 'Australia':
                        return 'label label-success';
                    case 'Africa':
                        return 'label label-default';
                    case 'Asia':
                        return 'label label-warning';
                    default:
                        return 'label label-primary';
                }
            },
            typeahead: {
                ajax: {
                    url: './rest/labels'
                }
            }
        });
        $scope.saveDocument = function(model) {
            model.document.labels = $("#labels").tagsinput('items');
            this.uploadFile(model);
        };
        $scope.uploadFile = function(model) {
            var file = $scope.myFile;
            console.log(file) //{webkitRelativePath: "", lastModified: 1430866129000, lastModifiedDate: Tue May 05 2015 23:48:49 GMT+0100 (IST), name: "Page 1&2.png", type: "image/png"…}
            $scope.model.document.name = file.name;
            console.log('file is ' + JSON.stringify(file));
            var uploadUrl = "./rest/file";
            fileUpload.uploadFileToUrl(file, uploadUrl, model);
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
                    scope.model.document.name = element[0].files[0].name;
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
        this.uploadFileToUrl = function(file, uploadUrl, model) {
            var fd = new FormData();
            fd.append('file', file);
            $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            }).success(function(result) {
                model.temp.showSuccessMessage = true;
                model.temp.showErrorMessage = false;
                console.log("File saved successfully...." + result);
                console.log("Saving document....");
                model.document.url = result.url
                var data = []
                data.push(model.document)
                $http.post("./rest/documents", JSON.stringify(data))
                    .success(function() {
                        model.temp.showSuccessMessage = true;
                        model.temp.showErrorMessage = false;
                        console.log("Document saved successfully....");
                    }).error(function(err) {
                        model.temp.showErrorMessage = true;
                        model.temp.showSuccessMessage = false;
                        model.temp.errorMessage = "Error in saving document...." + err;
                        console.log(model.temp.errorMessage);
                    });
            }).error(function(err) {
                model.temp.showErrorMessage = true;
                model.temp.showSuccessMessage = false;
                model.temp.errorMessage = "Error in saving file...." + err;
                console.log(model.temp.errorMessage);

                console.log(err);
            });
        }
    }
]);