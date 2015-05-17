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

        function createHtml(docs) {
            var html = "<table>";
            var i = 0;
            for (i = 0; i < docs.length; i++) {
                html = html + "<tr><td><div><img src='" + docs[i].url + "' class='img-thumbnail' alt='" + docs[i].name + "' height='100px' width='100px' /></div></td><td><div><label>" + docs[i].name + "</label><div>" + docs[i].description + "</div></div></td><td><div><a href='" + docs[i].downloadUrl + "'>Download</a></div></td></tr>";
            }
            html = html + "</table>";
            return html;
        }

        function displayResult(item) {
            $('.alert').show().html('You selected <strong>' + item.value + '</strong>');
        }

        $("#name").typeahead({
            ajax: {
                url: './rest/documents',
                method: 'get',
                triggerLength: 1,
                preDispatch: function(query) {
                    // console.log("predispatch query: " + query);
                    $scope.documents = [];
                    $("#queryResult").html("");
                    return {
                        query: query
                    }
                },
                preProcess: function(data) {
                    var transformedData = data.map(function(obj) {
                        obj.downloadUrl = "." + encodeURI(obj.url) + "/attachment";
                        obj.url = "." + encodeURI(obj.url) + "/inline";
                        return obj.name + " " + obj.description;
                    })
                    $scope.documents = data;
                    console.log("preProcess data: " + data);
                    $("#queryResult").html(createHtml(data));
                    // We good!
                    return transformedData;
                }
            },
            onSelect: displayResult,
            items: 15


        });
    }
]);