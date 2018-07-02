/** Controllers */
angular.module('app', ['ui.bootstrap', 'CalcService'])
.controller('SuppliesCtrl', function ($uibModal, calcService) {
    var vm = this;
    vm.supplies = calcService.loadSupplies();
    vm.products = calcService.loadProducts();

    vm.open = function () {

        var modalInstance = $uibModal.open({
          animation: false,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'modal.html',
          controller: 'ModalInstanceCtrl',
          controllerAs: 'vm'
        });

        modalInstance.result.then(function (item) {
          console.log(item);
        }, function () {
          // console.log('Modal dismissed at: ' + new Date());
        });
  };

});

angular.module('app').controller('ModalInstanceCtrl', function ($uibModalInstance) {
  var vm = this;
  vm.insumo = {};

  vm.ok = function () {
    $uibModalInstance.close(vm.insumo);
  };

  vm.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
