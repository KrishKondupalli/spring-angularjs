'use strict';

jhipsterApp.factory('Category', function ($resource) {
        return $resource('app/rest/categorys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
