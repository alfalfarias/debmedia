/** Controllers */
angular.module('app')
.controller('SuppliesCtrl', function ($uibModal, suppliesService) {
    let vm = this;

    vm.loadSupplies = function () {
      vm.supplies = suppliesService.loadSupplies();
    }

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
          vm.loadSupplies();
        }, function () {
          // console.log('Modal dismissed at: ' + new Date());
        });
    };

  vm.openDelete = function (supply, index) {

    let modalInstance = $uibModal.open({
      animation: true,
      ariaLabelledBy: 'modal-title',
      ariaDescribedBy: 'modal-body',
      templateUrl: 'confirmodal.html',
      size: 'md',
      controller: function(suppliesService, $uibModalInstance) {
        let vm = this;
        vm.item = supply;

        vm.delete = function () {
          suppliesService.deleteSupply(vm.item.id);
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
        vm.supplies.splice(index, 1);
      }
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
