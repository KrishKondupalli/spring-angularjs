'use strict';

jhipsterApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/image', {
                    templateUrl: 'views/images.html',
                    controller: 'ImageController',
                    resolve:{
                        resolvedImage: ['Image', function (Image) {
                            return Image.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
