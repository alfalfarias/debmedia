angular.module('ProductsService', [])
.service('productsService', function($http){

	this.products = [{
	        "id": 1,
	        "name": "Producto 1",
	        "supplies": [{
	        	"id": 1,
		        "name": "Insumo 1",
		        "quantity": 15
	        },
	        {
	        	"id": 2,
		        "name": "Insumo 2",
		        "quantity": 13
	        }],
	        "price": 3520
	    },
	    {
	        "id": 2,
	        "name": "Producto 2",
	        "supplies": [{

	        	"id": 3,
		        "name": "Insumo 3",
		        "quantity": 10
	        }],
	        "price": 2500
	    },
	    {
	        "id": 3,
	        "name": "Producto 3",
	        "supplies": [{
	        	"id": 3,
		        "name": "Insumo 3",
		        "quantity": 10
	        },
	        {
	        	"id": 4,
		        "name": "Insumo 4",
		        "quantity": 6
	        }],
	        "price": 5000
	}];


   this.loadProducts = function () {
   	return this.products;
   };

   this.setProduct = function (item) {
   	console.log(item);
   	// return '201 CREATED';
   };

   this.uploadProduct = function (item) {
   	console.log(item);
   	// return '200 OK';
   };

   this.deleteProduct = function (id) {
   	console.log(id);
   };

});