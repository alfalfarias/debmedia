/** Controllers */
angular.module('app')
.controller('ProductCtrl', function ($uibModal, productsService) {
    let vm = this;

    vm.navbarurl = "../../../app/views/navbar.html";

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

        modalInstance.result.then(function () {
          vm.loadProducts();
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
    if(!vm.edit) {
      productsService.setProduct(vm.product);
    }else {
      productsService.uploadProduct(vm.product);
    }
    $uibModalInstance.close();
  };

  vm.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
