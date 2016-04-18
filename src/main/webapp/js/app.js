(function(angular) {
	
  var app = angular.module('controllerCalculator', []);
  
  app.controller('SettingsController', ['$scope', SettingsController]);

function SettingsController($scope) {
  $scope.name = "";
  $scope.calculator = [
    {type:'', value:'',operation:''}];


  $scope.addContact = function() {
    $scope.calculator.push({type:'', value:''});
  };

  $scope.removeContact = function(calculatorToRemove) {
    var index = $scope.calculator.indexOf(calculatorToRemove);
    $scope.calculator.splice(index, 1);
  };

  $scope.clearContact = function(calculator) {
    calculator.type = '';
    calculator.value = '';
    calculator.operation = '';
  };

}

})(window.angular);
