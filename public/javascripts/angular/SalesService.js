angular.module('SalesService', [])
.service('salesService', function($http){

	this.url = 'http://localhost:9000/sales';

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

   this.uploadProduct = function (item) {
   	console.log(item);
   	// return '200 OK';
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