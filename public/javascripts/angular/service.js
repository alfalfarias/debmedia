angular.module('CalcService', [])
.service('calcService', function($http){

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

	this.products = [{
	        "id": 1,
	        "name": "Producto 1",
	        "supplies": [{
	        	"name": "Insumo 1"
	        },
	        {
	        	"name": "Insumo 2"
	        }],
	        "price": 3520
	    },
	    {
	        "id": 2,
	        "name": "Producto 2",
	        "supplies": [{
	        	"name": "Insumo 3"
	        }],
	        "price": 2500
	    },
	    {
	        "id": 3,
	        "name": "Producto 3",
	        "supplies": [{
	        	"name": "Insumo 3"
	        },
	        {
	        	"name": "Insumo 4"
	        }],
	        "price": 5000
	}];


   this.loadSupplies = function() {
      return this.supplies;
   }

   this.loadProducts = function () {
   	return this.products;
   }

});