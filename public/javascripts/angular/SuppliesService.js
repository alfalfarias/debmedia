angular.module('SuppliesService', [])
.service('suppliesService', function($http){

   this.url = 'http://localhost:9000/supplies';

   this.loadSupplies = function() {
      return $http.get(this.url)
      .then(function successCallback(response){
      	return response.data;
      }, function errorCallback(error){
      	return error;
      });
   };

   this.setSupply = function (data) {
   	return $http.post(this.url, data)
      .then(function success(response) {
         return response;
      }, function error(error){
         return error;
      });
   	// return '201 CREATED';
   };

   this.uploadSupply = function (data) {
   	// console.log(item);
   	// return '200 OK';
   }

   this.deleteSupply = function (id) {
   	return $http.delete(this.url+'/'+id)
      .then(function success(response) {
         return response;
      }, function error(error) {
         return error;
      });
   };

});