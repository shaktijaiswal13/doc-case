'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'myApp.Documents',
    'myApp.DocumentUpload'
]).
config(['$routeProvider',
    function($routeProvider) {
        //     redirectTo: '/view1'
    }
]);