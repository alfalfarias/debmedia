/** Controllers */
angular.module('app')
.controller('SuppliesCtrl', function ($uibModal, suppliesService) {
    let vm = this;
    vm.supplies = suppliesService.loadSupplies();

    vm.open = function (supplie) {

      let item = {};

      if(supplie !== undefined){
        item = supplie;
      }

        let modalInstance = $uibModal.open({
          animation: true,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'modal.html',
          controller: 'SupModalInstanceCtrl',
          controllerAs: 'vm', 
          resolve: {
            supply: function () {
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

.controller('SupModalInstanceCtrl', function ($uibModalInstance, supply, suppliesService) {

  let vm = this;
  vm.insumo = {};

  if(supply.name){
    vm.edit = true;
    angular.copy(supply, vm.insumo);
  }

  vm.ok = function () {
    if(!vm.edit) {
      suppliesService.setSupply(vm.insumo);
    }else {
      suppliesService.uploadSupply(vm.insumo);
    }
    $uibModalInstance.close(vm.insumo);
  };

  vm.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
