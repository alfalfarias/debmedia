/** Controllers */
angular.module('app')
.controller('ProductCtrl', function ($uibModal, productsService, toastr) {
    let vm = this;

    vm.loadProducts = function () {
      productsService.loadProducts()
      .then(function (data) {
        vm.products = data.products; 
      });
    };
    
    vm.open = function (product) {

      let item = {};

      if(product !== undefined){
        item = product;
      }

        let modalInstance = $uibModal.open({
          animation: true,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'modal.html',
          controller: 'ProModalInstanceCtrl',
          controllerAs: 'vm', 
          resolve: {
            product: function () {
              return item;
            }
          }
        });

        modalInstance.result.then(function (data) {
          if(data.data.message){
            toastr.success('Producto registrado correctamente', 'Bien');
            vm.loadProducts();
          }else {
            for (let prop in data.data) {
                if(data.data[prop].length > 1){
                  for (let i = 0; i < data.data[prop].length; i++) {
                    toastr.error(data.data[prop][i], 'Error');
                  }
                }else {
                  toastr.error(data.data[prop][0], 'Error');
                }
            }
          }
        }, function () {
      // console.log('Modal dismissed at: ' + new Date());
        });
    };

    vm.openDelete = function (product, index) {

    let modalInstance = $uibModal.open({
      animation: true,
      ariaLabelledBy: 'modal-title',
      ariaDescribedBy: 'modal-body',
      templateUrl: 'confirmodal.html',
      size: 'md',
      controller: function(productsService, $uibModalInstance) {
        let vm = this;
        vm.item = product;

        vm.delete = function () {
          productsService.deleteProduct(vm.item.id);
          $uibModalInstance.close();
        };

        vm.cancel = function () {
          $uibModalInstance.close();
        }

      },
      controllerAs: 'vm'
    });

     modalInstance.result.then(function () {
      if(index !== undefined){
        toastr.warning('Producto eliminado correctamente', 'Eliminado');
        vm.products.splice(index, 1);
      }
    }, function () {
      // console.log('Modal dismissed at: ' + new Date());
    });

  };

})

.controller('ProModalInstanceCtrl', function ($uibModalInstance, product, productsService, suppliesService) {

  let vm = this;

  suppliesService.loadSupplies()
  .then(function(data){
    vm.supplies = data.supplies;
  });

  vm.product = {};

  if(product.name){
    vm.edit = true;
    angular.copy(product, vm.product);
  }

  vm.ok = function () {
    angular.forEach(vm.product.productSupplies, function(value, key) {
      vm.product.productSupplies[key].quantity = 1;
      });
    if(!vm.edit) {
      productsService.setProduct(vm.product)
      .then(function (response) {
        $uibModalInstance.close(response);
      });
    }else {
      productsService.uploadProduct(vm.product)
      .then(function (response) {
        $uibModalInstance.close(response);
      });
    }
  };

  vm.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
