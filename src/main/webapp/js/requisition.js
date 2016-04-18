
var app = angular.module('requisition', [], function($httpProvider) {
	$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
});
app.controller('myCtrl', function($scope, $http,$window) {
	$scope.unit=0;
	$scope.priceperunit=0;
	$scope.frombalance=0;
	$scope.tobalance=0;
	$scope.day="";
	$scope.amount = 0;
	$scope.docno = "";
	$scope.projectchange = function() {
		
			$scope.gcostcode = '';
			
			$http({
		          method: "POST", 
		          url: "ajax_requisition.jsp",
		          params:{"projectCode":$scope.project.split(' - ')[0],"year":$scope.project.split(' - ')[1],"ajax_type":"select"},
		          headers: {"Accept-Charset":"charset=utf-8",'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}
		          
		        }).then(function(response) {
		        	$scope.status = response.status;
		            $scope.datas = response.data;
		            
		        });
		}
		
	$scope.gcostcodechange = function() { 
	
		$http({
	          method: "POST", 
	          url: "ajax_frombudget.jsp",
	          params:{"projectCode":$scope.project.split(' - ')[0],"year":$scope.project.split(' - ')[1],"gcostcode":$scope.gcostcode},
	          headers: {"Accept-Charset":"charset=utf-8",'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}
	          
	        }).then(function(response) {
	            $scope.frombalance = response.data; 
	        });
	}
	
	$scope.addrequisition = function() {
		
		
		$http({
	          method: "POST", 
	          url: "ajax_requisition.jsp",
	          params:{"projectCode":$scope.project.split(' - ')[0],
					"year":$scope.project.split(' - ')[1],
					"gcostcode":$scope.gcostcode,
					"unit":$scope.unit,
					"priceperunit":$scope.priceperunit,
					"frombalance":$scope.frombalance,
					"tobalance":$scope.tobalance,
					"day":$scope.day,
					"requisiton_type":$scope.requisiton_type,
					"description":$scope.description,
					"amount":$scope.amount,
					"ajax_type":"add",
					"docno":$scope.docno}
	          
	        }).then(function(response) {
	            $scope.adddata = response.data
	            $scope.docno = $scope.adddata.docno;
	            $scope.selectrequisition($scope.docno,$scope.project.split(' - ')[0],$scope.project.split(' - ')[1]);
	            $scope.clearinput();
	            alert("เพิ่มข้อมูลสำเร็จ");
	        });
			
		
	}
	
	$scope.selectrequisition = function(docno,project_code,project_year){
		$http({
	          method: "POST", 
	          url: "ajax_requisition.jsp",
	          params:{"ajax_type":"selectlist",
					"docno":docno,
					"projectCode":project_code,
					"year":project_year},
	          headers: {"Accept-Charset":"charset=utf-8",'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}
	          
	        }).then(function(response) {
	            $scope.selectlist = response.data
	        });
	}
	
	$scope.deleterequisition = function(docno,gcostcode){
		$http({
	          method: "POST", 
	          url: "ajax_requisition.jsp",
	          params:{"ajax_type":"delete",
					"docno":docno,
					"gcostcode":gcostcode,
					"projectCode":$scope.project.split(' - ')[0],
					"year":$scope.project.split(' - ')[1]},
	          headers: {"Accept-Charset":"charset=utf-8",'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}
	          
	        }).then(function(response) {
	        	$scope.deleted = response.data;
	        	$scope.clearinput();
	        	$scope.selectrequisition(docno,$scope.project.split(' - ')[0],$scope.project.split(' - ')[1]);
	        });
	}
	
	$scope.clearinput = function(){
		$scope.unit=0;
		$scope.priceperunit=0;
		$scope.frombalance=0;
		$scope.tobalance=0;
		$scope.day="";
		$scope.amount = 0;
		$scope.gcostcode = "";
		$scope.requisiton_type = "";
		$scope.description = "";
	}
	
	$scope.print = function(){
		$window.open("report/reprequisition.jsp?requisition_docno="+$scope.docno+"&project_code="+$scope.project.split(' - ')[0]+"&project_year="+$scope.project.split(' - ')[1]);
	}

});