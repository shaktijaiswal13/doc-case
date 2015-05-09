'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'myApp.Documents',
    'myApp.DocumentUpload',
    'myApp.DocumentCreate',
]).
config(['$routeProvider',
    function($routeProvider) {
        //     redirectTo: '/view1'
    }
]);