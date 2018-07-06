/** Controllers */
angular.module('app')
.controller('SalesCtrl', function ($uibModal, salesService, toastr) {
    let vm = this;

    vm.loadSales = function () {
      salesService.loadSales()
      .then(function (data) {
        vm.sales = data.sales; 
      });
    };
    
    vm.open = function () {

        let modalInstance = $uibModal.open({
          animation: true,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'modal.html',
          controller: 'SellModalInstanceCtrl',
          controllerAs: 'vm'
        });

        modalInstance.result.then(function (data) {
          if(data.data.message){
            toastr.success('Venta realizada correctamente', 'Bien');
            vm.loadSales();
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

})

.controller('SellModalInstanceCtrl', function ($uibModalInstance, productsService, salesService) {

  let vm = this;

  productsService.loadProducts()
  .then(function(data){
    vm.products = data.products;
  });

  vm.sale = {};

  vm.ok = function () {
    let data = {};
    angular.copy(vm.sale, data)
    delete data.saleProduct.productSupplies;
    salesService.setSell(data)
    .then(function (response) {
      $uibModalInstance.close(response);
    });
  };

  vm.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
