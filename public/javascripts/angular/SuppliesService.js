angular.module('SuppliesService', [])
.service('suppliesService', function($http){

	this.supplies = [{
	        "id": 1,
	        "name": "Insumo 1",
	        "quantity": 15
	    },
	    {
	        "id": 2,
	        "name": "Insumo 2",
	        "quantity": 13
	    },
	    {
	        "id": 3,
	        "name": "Insumo 3",
	        "quantity": 10
	    },
	    {
	        "id": 4,
	        "name": "Insumo 4",
	        "quantity": 6
	}];


   this.loadSupplies = function() {
      return $http.get('http://localhost:9000/supplies')
      .then(function successCallback(response){
      	return response.data;
      }, function errorCallback(error){
      	console.log(error);
      	return 'error';
      });
   };

   this.setSupply = function (item) {
   	console.log(item);
   	// return '201 CREATED';
   };

   this.uploadSupply = function (item) {
   	// console.log(item);
   	// return '200 OK';
   }

   this.deleteSupply = function (id) {
   	// console.log(id);
   	// return '200 OK';
   };

});