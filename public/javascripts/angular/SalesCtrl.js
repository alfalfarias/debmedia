/** Controllers */
angular.module('app')
.controller('SalesCtrl', function ($uibModal, salesService, toastr) {
    let vm = this;

    // vm.loadProducts = function () {
    //   productsService.loadProducts()
    //   .then(function (data) {
    //     vm.products = data.products; 
    //   });
    // };
    
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
          controller: 'SellModalInstanceCtrl',
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
            toastr.error(data.data.quantity[0], 'Error');
          }
        }, function () {
      // console.log('Modal dismissed at: ' + new Date());
        });
    };

  //   vm.openDelete = function (product, index) {

  //   let modalInstance = $uibModal.open({
  //     animation: true,
  //     ariaLabelledBy: 'modal-title',
  //     ariaDescribedBy: 'modal-body',
  //     templateUrl: 'confirmodal.html',
  //     size: 'md',
  //     controller: function(productsService, $uibModalInstance) {
  //       let vm = this;
  //       vm.item = product;

  //       vm.delete = function () {
  //         productsService.deleteProduct(vm.item.id);
  //         $uibModalInstance.close();
  //       };

  //       vm.cancel = function () {
  //         $uibModalInstance.close();
  //       }

  //     },
  //     controllerAs: 'vm'
  //   });

  //    modalInstance.result.then(function () {
  //     if(index !== undefined){
  //       toastr.warning('Producto eliminado correctamente', 'Eliminado');
  //       vm.products.splice(index, 1);
  //     }
  //   }, function () {
  //     // console.log('Modal dismissed at: ' + new Date());
  //   });

  // };

})

.controller('SellModalInstanceCtrl', function ($uibModalInstance, product, productsService, salesService) {

  let vm = this;

  productsService.loadProducts()
  .then(function(data){
    vm.products = data.products;
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
