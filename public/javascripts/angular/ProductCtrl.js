/** Controllers */
angular.module('app')
.controller('ProductCtrl', function ($uibModal, productsService) {
    let vm = this;
    vm.products = productsService.loadProducts();

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

        modalInstance.result.then(function (item) {
        }, function () {
          // console.log('Modal dismissed at: ' + new Date());
        });
    };

})

.controller('ProModalInstanceCtrl', function ($uibModalInstance, product, productsService, suppliesService) {

  let vm = this;
  vm.insumos = suppliesService.loadSupplies();
  vm.product = {};

  if(product.name){
    vm.edit = true;
    angular.copy(product, vm.product);
  }

  vm.ok = function () {
    if(!vm.edit) {
      productsService.setProduct(vm.product);
    }else {
      productsService.uploadProduct(vm.product);
    }
    $uibModalInstance.close(vm.product);
  };

  vm.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
