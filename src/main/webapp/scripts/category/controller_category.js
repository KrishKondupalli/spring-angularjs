'use strict';

jhipsterApp.controller('CategoryController', function ($scope, resolvedCategory, Category) {

        $scope.categorys = resolvedCategory;

        $scope.create = function () {
            Category.save($scope.category,
                function () {
                    $scope.categorys = Category.query();
                    $('#saveCategoryModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.category = Category.get({id: id});
            $('#saveCategoryModal').modal('show');
        };

        $scope.delete = function (id) {
            Category.delete({id: id},
                function () {
                    $scope.categorys = Category.query();
                });
        };

        $scope.clear = function () {
            $scope.category = {id: null, sampleTextAttribute: null, sampleDateAttribute: null};
        };
    });
