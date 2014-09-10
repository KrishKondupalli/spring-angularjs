'use strict';

jhipsterApp.controller('ImageController', function ($scope, resolvedImage, Image) {

        $scope.images = resolvedImage;

        $scope.create = function () {
            Image.save($scope.image,
                function () {
                    $scope.images = Image.query();
                    $('#saveImageModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.image = Image.get({id: id});
            $('#saveImageModal').modal('show');
        };

        $scope.delete = function (id) {
            Image.delete({id: id},
                function () {
                    $scope.images = Image.query();
                });
        };

        $scope.clear = function () {
            $scope.image = {id: null, sampleTextAttribute: null, sampleDateAttribute: null};
        };
    });
