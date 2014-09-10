'use strict';

jhipsterApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/category', {
                    templateUrl: 'views/categorys.html',
                    controller: 'CategoryController',
                    resolve:{
                        resolvedCategory: ['Category', function (Category) {
                            return Category.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
