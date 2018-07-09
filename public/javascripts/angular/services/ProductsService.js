angular.module('ProductsService', [])
.service('productsService', function($http, $window){

   this.url = $window.location.origin+'/products';

   this.loadProducts = function () {
   	return $http.get(this.url)
      .then(function successCallback(response){
      	return response.data;
      }, function errorCallback(error){
      	return error;
      });
   };

   this.setProduct = function (data) {
   	return $http.post(this.url, data)
   	  .then(function success(response){
   	  	return response;
   	  }, function error(error){
   	  	return error;
   	  });
   	// return '201 CREATED';
   };

   this.uploadProduct = function (data) {
   	return $http.put(this.url+'/'+data.id, data)
        .then(function success(response){
         return response;
        }, function error(error){
         return error;
      });
   };

   this.deleteProduct = function (id) {
   	return $http.delete(this.url+'/'+id)
      .then(function success(response) {
         return response;
      }, function error(error) {
         return error;
      });
   };

});