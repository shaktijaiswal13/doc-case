'use strict';

angular.module('myApp.DocumentsSearch', ['ngRoute'])

.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.when('/documentssearch', {
            templateUrl: 'js/views/DocumentsSearch/DocumentsSearchView.html',
            controller: 'documentsSearchCtrl'
        });
    }
])

.controller('documentsSearchCtrl', ['$scope', '$http',
    function($scope, $http) {
        $scope.model = {
            result: "not result"
        };
        $scope.search = function(model) {
            uploadFile(model);
        };

        function displayResult(item) {
            $('.alert').show().html('You selected <strong>' + item.value + '</strong>');
        }

        $("#name").typeahead({
            ajax: {
                url: './rest/documents',
                method: 'get',
                triggerLength: 1,
                preDispatch: function(query) {
                    console.log("predispatch query: " + query);
                    $("#queryResult").text("");
                    return {
                        query: query
                    }
                },
                preProcess: function(data) {
                    var transformedData = data.map(function(obj) {
                        return obj.name + " " + obj.description;
                    })
                    console.log("preProcess data: " + data);
                    $("#queryResult").text(JSON.stringify(transformedData));
                    // We good!
                    return transformedData;
                }
            },
            onSelect: displayResult


        });
    }
]);