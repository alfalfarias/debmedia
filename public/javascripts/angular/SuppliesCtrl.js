/** Controllers */
angular.module('app')
.controller('SuppliesCtrl', function ($uibModal, suppliesService, toastr) {
    let vm = this;

    vm.loadSupplies = function () {
      suppliesService.loadSupplies()
      .then(function(data) {
        vm.supplies = data.supplies;
      });
    };

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

        modalInstance.result.then(function (data) {
          if(data.data.message){
            toastr.success('Insumo registrado correctamente', 'Bien');
            vm.loadSupplies();
          }else {
            toastr.error(data.data.quantity[0], 'Error');
          }
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
        toastr.warning('Insumo eliminado correctamente', 'Eliminado');
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
      suppliesService.setSupply(vm.insumo)
      .then(function (response) {
        $uibModalInstance.close(response);
      });
    }else {
      suppliesService.uploadSupply(vm.insumo)
      .then(function (response) {
        $uibModalInstance.close(response);
      });
    }
  };

  vm.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
