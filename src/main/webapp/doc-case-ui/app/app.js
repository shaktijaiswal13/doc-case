'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'myApp.directives',
    'myApp.Documents',
    'myApp.DocumentsSearch',
    'myApp.DocumentCreate'
]).
config(['$routeProvider',
    function($routeProvider) {
        redirectTo: '#'
    }
]);