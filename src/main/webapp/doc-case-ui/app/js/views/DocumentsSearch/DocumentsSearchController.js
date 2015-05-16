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


        function displayResult(item) {
            $('.alert').show().html('You selected <strong>' + item.value + '</strong>: <strong>' + item.text + '</strong>');
        }

        $("#name").typeahead({
            ajax: {
                url: './rest/documents',
                method: 'get',
                triggerLength: 3,
                preDispatch: function(query) {
                    console.log("predispatch query: " + query);
                    return {
                        query: query
                    }
                },
                preProcess: function(data) {
                    console.log("preProcess data: " + data);
                    $("#queryResult").text(data);
                    if (data.success === false) {
                        // Hide the list, there was some error
                        return false;
                    }
                    // We good!
                    return data.mylist;
                }
            },
            onSelect: displayResult


        });
    }
]);