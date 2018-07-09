angular.module('SalesService', [])
.service('salesService', function($http, $window){

   this.url = $window.location.origin+'/sales';

   this.loadSales = function () {
   	return $http.get(this.url)
      .then(function successCallback(response){
      	return response.data;
      }, function errorCallback(error){
      	return error;
      });
   };

   this.setSell = function (data) {
   	return $http.post(this.url, data)
   	  .then(function success(response){
   	  	return response;
   	  }, function error(error){
   	  	return error;
   	  });
   	// return '201 CREATED';
   };
   
});