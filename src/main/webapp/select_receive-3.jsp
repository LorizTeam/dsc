<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>หน้ารับสินค้า</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
	
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet"> 
		<link href="css/metro-schemes.css" rel="stylesheet"> 
	 	<link href="css/sweetalert.css" rel="stylesheet" />
	 
		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script> 
 		<script src="js/jquery.dataTables.min.js"></script>  
		<script src="js/sweetalert.min.js"></script>
  </head>
  
  <script>
        function no_submit(){
            return false;
        }

        function notifyOnErrorInput(input){
            var message = input.data('validateHint');
            $.Notify({
                caption: 'Error',
                content: message,
                type: 'alert'
            });
        }
        function changeCoin(){
        	var amtt = document.getElementById("amtt").value;
        	var receiveAmt = document.getElementById("receiveAmt").value; 
        	
        	var amtt = amtt.replace(",", ""); 
        	var receiveAmt = receiveAmt.replace(",", "");
        	
        	var changeCoin = receiveAmt-amtt;
        	document.getElementById("change").value = changeCoin;
        	if(receiveAmt==0){
        		document.getElementById("change").value = '';
        	}
        }
        function printreceive() {  
        	var chk1 = document.getElementById("receiveAmt").value; 
        	if(chk1!=''){
        	
        	var para1 = document.getElementById("docNoHD").value; 
        	var para2 = document.getElementById("amtt").value;
        	
    		var load = window.open('/pcpnru/receiveReport.action?'+ 
    				
    				'&docNoHD='+para1+
    				'&amtt='+para2
    				,'scrollbars=yes,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no');
        	}
        }
        function printRev(tdocNo, tprojectCode){
        	var torn = document.getElementById("torn").value;
        	var receiveAmt = document.getElementById("receiveAmt").value; 
        	var amt = document.getElementById("amtt").value;  
        	
        	if(parseFloat(receiveAmt)>=parseFloat(amt)&&receiveAmt!='0'&&amt!='0'){ 
        	
    		swal({  title: "ยืนยันการพิมพ์เอกสาร ?",   
    				text: "หากคุณต้องการพิมพ์เอกสารให้กดปุ่มยืนยัน !",   
    				type: "warning",   
    				showCancelButton: true,   
    				confirmButtonColor: "#DD6B55",   
    				confirmButtonText: "ยืนยัน, ฉันต้องการพิมพ์เอกสาร !",   
    				cancelButtonText: "ไม่, ฉันไม่ต้องการพิมพ์เอกสาร !",   
    				closeOnConfirm: false,   
    				closeOnCancel: false,
    				showLoaderOnConfirm: true
    			}, 
    			
    		function (isConfirm){
    			 
    		  	if (isConfirm) {
    			setTimeout(function(){
    				var load = window.open("/pcpnru/receiveReport.action?docNoHD="+tdocNo+"&projectcode="+tprojectCode+"&receiveAmt="+receiveAmt+"&torn="+torn+"" 
    						,'scrollbars=yes,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no');
    			swal("พิมพ์เอกสารสำเร็จแล้ว!", "โปรดตรวจสอบรายละเอียดของเอกสารอีกครั้งเพื่อความถูกต้อง !", "success");
    			} 
    			 , 1000);
     
    			}else {    
    			 swal("ยกเลิกการพิมพ์เอกสาร", "คุณสามารถพิมพ์เอกสารได้อีกครั้งหลังจากปิดหน้าต่างนี้ !", "error");   
    			}
    		});
    		
        	}else if(receiveAmt=='0'&&torn=='0'){ 
        		swal({
        			  title: 'ยังไม่ได้ทำรายการ',
        			  text: 'กรุณากรอกข้อมูลให้ครบถ้วน',
        			  closeOnConfirm: true,
        			  timer: 2500
        			})
        		
        	}else{
        		var valamt = amt-receiveAmt;
        		valamt = parseFloat(valamt).toLocaleString("en-US");
        		swal({
        			  title: 'จำนวนเงินไม่เพียงพอ',
        			  text: 'ขาดอีก '+valamt+' บาท',
        			  closeOnConfirm: true,
        			  timer: 3000
        			})
        		
        	}
        } 
    </script>
  
  <body ng-app="myApp" ng-controller="myCtrl">
  
  	<% 		  
  			String docNo		= (String) request.getAttribute("docNo");
  			String projectCode 	= (String) request.getAttribute("projectCode");
  			String[] splitgprojectcode = projectCode.split(" - ");
  			String dateTime 	= (String) request.getAttribute("dateTime");
  			String gcostCode 	= (String) request.getAttribute("gcostCode");
  		//	String amountfrom 	= (String) request.getAttribute("amountfrom");
  		//	String local 		= (String) request.getAttribute("local"); 
  			String amtt 		= (String) request.getAttribute("amtt");
  			String standardprice 		= (String) request.getAttribute("standardprice");
  			
  			Receive2DB rcM = new Receive2DB();
  			if(amtt==null) { 
  				amtt	= rcM.getSumAmount(docNo, splitgprojectcode[0]);
  			}
  			String receiveAmt =  rcM.getReceiveAmount(docNo, splitgprojectcode[0]);
  			
  			List ReceiveList1 = null;
  			if (request.getAttribute("ReceiveList") == null) { 
  				ReceiveList1 = rcM.GetReceiveList(docNo, splitgprojectcode[0]);
  			}else{
  				ReceiveList1 = (List) request.getAttribute("ReceiveList");
  			}
  	%>
  
    <div><%@include file="topmenu.jsp" %></div>
	<br> 
	<h3 class="align-center">บันทึกรายได้</h3>
	<div ng-init="n1,n2,total,desc,i_no">
	<div class="example" data-text="รายการรับ">
	<div class="grid">
	<form action="selectreceive.action" method="post" data-role="validator" data-show-required-state="false" data-hint-mode="line" data-hint-background="bg-red" data-hint-color="fg-white" data-hide-error="5000">
		  	<div class="row cells11"> 
		    	<div class="cell colspan3" > 
		       		  โครงการ<div class="input-control full-size "> 
		       		 	<input type="text" name="projectCode" value="<%=projectCode%>" readonly="readonly">
					 </div> 
		    	</div>   
		    	<div class="cell colspan3">
		    		 ค่าใช้จ่าย<div class="input-control full-size "> 
		    		 	<input type="text" name="gcostCode" value="<%=gcostCode%>" readonly="readonly">  
					</div> 
		    	</div>   
				<div class="cell colspan2">  
		        	 วันที่การรับ<div class="input-control full-size "> 
                        <input type="text" name="dateTime" value="<%=dateTime%>" readonly="readonly"> 
                    </div> 
				</div> 
		    	<div class="cell colspan1">  
		        	เลขที่เอกสาร<div class="input-control full-size "> 
                        <input type="text" name="docNo" value="<%=docNo%>" readonly="readonly"> 
                    </div>
				</div> 
				<div class="cell colspan2">  
		        	เลขที่เล่ม<div class="input-control full-size "> 
                        <s:textfield name="receiveform.vol" readonly="true" /> 
                    </div>
				</div>
			</div>  
		  	<div class="row cells12">  
		  		<div class="cell colspan3">
		    		ค่าใช้จ่าย <div class="input-control full-size "> 
					    <select name="costcode" id="costcode" ng-model="percent" ng-change="n2=(n2*percent/100)+n2">
					    	<option value="">--กรุณาเลือกรายการ--</option>
					    <%
					    List Lcostcode_forreceive2 = null;
					    if(request.getAttribute("Lcostcode_forreceive2") != null){
					    	Lcostcode_forreceive2 = (List) request.getAttribute("Lcostcode_forreceive2");
					    	Iterator lcostcodeIterate = Lcostcode_forreceive2.iterator();
					    	while(lcostcodeIterate.hasNext()){
					    		ReceiveForm rcf = (ReceiveForm) lcostcodeIterate.next();
					    %>
					    		<option value="<%=rcf.getPercentprice()%>"><%=rcf.getCostcode()%> - <%=rcf.getCostname()%></option>
					    <%
					    	}
					    }
					    
					    %>
					    </select>
					    
					    <s:hidden name="receiveform.costcode" id="hiddencostcode"/>
					</div>
		    	</div>
		    	<div class="cell colspan3">
		    		ได้รับเงินจาก<div class="input-control full-size "> 
					    <s:textfield name="receiveform.amountfrom" readonly="readonly" />
					</div>
		    	</div> 
		    	<div class="cell colspan3">
		    		สถานที่<div class="input-control full-size "> 
					    <s:textfield name="receiveform.local" readonly="readonly" />
					</div>
		    	</div>
		    	<div class="cell colspan3"><br>
                     <label class="input-control radio small-check" ng-init="r1=1">
                     	<input type="radio" name="receivedetail" value="1" ng-model="r1" checked="checked" data-validate-func="required" data-validate-hint="This field can not be empty">
                     	<span class="check"></span></label><span class="leaf"> เงินสด </span> 
                     <label class="input-control radio small-check">
                     	<input type="radio" name="receivedetail" ng-model="r1" value="2"> 
                     	<span class="check"></span></label><span class="leaf"> โอน </span> 
		    	</div>  
		    </div>
		  	<div class="row cells10"> 
		    	<div class="cell colspan10">
		    		รายละเอียด<div class="input-control full-size "> 
					    <input type="text" ng-model="desc" id="description" name="description" data-validate-func="required" data-validate-hint="This field can not be empty"> 
					</div>
		    	</div>
		    </div>  
		  	<div class="row cells12" >  
		    	<div class="cell colspan3">
		    		จำนวน<div class="input-control full-size ">
		    			<input type="text"ng-hide="true" ng-model="i_no" id="itemNo" name="itemNo"> 
					    <input type="number" id="qty" name="qty"ng-keyup="total=n1*n2" ng-model="n1"  data-validate-func="required" data-validate-hint="This field can not be empty">

					</div>
		    	</div> 
		    	<div class="cell colspan3">
		    		ราคาต่อหน่วย<div class="input-control full-size "> 
					    <input type="number" id="amount"ng-keyup="total=n1*n2" ng-model="n2" name="amount" data-validate-func="required" data-validate-hint="This field can not be empty">
					</div>
		    	</div> 
		    	<div class="cell colspan3"> 
		    		ราคารวม<div class="input-control full-size ">
		    		<h3 class="no-margin">{{total | currency:"฿"}}</h3>
					    <s:hidden id="amountTotal" name="amountTotal" value="{{total}}"/>
					</div>
		    	</div>  
		    </div>    
		    <div class="row">
		    	<div class="cell" align="center"><br>
					  <button class="button success" type="submit" name="add">บันทึกรายได้</button> 
					  <button class="button primary" type="submit" name="update">แก้ไขรายการ</button> 
					  <button class="button danger" type="submit" name="delete">ลบรายการ</button>
				</div>
		    </div>
		    <s:hidden name="receiveform.standardprice" id="hiddencostcode"/>
		    </form> 
		</div> <!-- End of example --> 
	</div>
	
	<div class="example" data-text="รายการ">
            <table id="table_receive" class="dataTable striped border bordered" data-role="datatable" data-searching="true">
                <thead>
                <tr>  
                	<th>เลขที่</th> 
                    <th>รายละเอียด</th>
                    <th>สถานะการจ่าย</th>
                    <th>จำนวน</th>
                    <th>ราคา</th>
                    <th>ราคารวม</th>
                </tr>
                </thead> 
                  
                <tbody>
                <%	if (ReceiveList1 != null) {
						List ReceiveList = ReceiveList1;
						int x = 0;
						for (Iterator iter = ReceiveList.iterator(); iter.hasNext();) {
						x++; 
						ReceiveForm revL = (ReceiveForm) iter.next();
				%>
                <tr ng-click="
                	n1=<%=revL.getQty()%>;
                	 n2=<%=revL.getAmount()%>;
                	 total=<%=revL.getAmountTotal()%>;
                	 r1='<%=revL.getReceivedetail()%>';
                	 desc='<%=revL.getDescription()%>';
                	 i_no='<%=revL.getItemNo()%>';
                	 ">  
                    <td class="tditemno" align="center"><%=revL.getItemNo()%> </td>
                    <td class="tddescription" align="center"><%=revL.getDescription()%></td>
                    <td class="tdreceivedetail" align="center">
                    	<%if(revL.getReceivedetail().equals("1")){%>เงินสด<%} else{%>โอน<%} %>
                    </td>
                    <td class="tdqty" align="center">{{<%=revL.getQty()%>| number:0}}</td>  
                    <td class="tdamount" align="center">{{<%=revL.getAmount()%> | currency:"฿"}}</td>
                    <td class="tdamountTotal" align="center">{{<%=revL.getAmountTotal()%> | currency:"฿"}}</td>
                </tr>  
                <% 	} %> 
	                
                <% }  else { %> 
                	<tr> 
                    <td colspan="5">ไม่พบข้อมูล</td> 
                	</tr> 
                <%	} %>	 
                </tbody>
            </table>
        </div> <!-- End of example table --> 
      </div>  
    <div class="example" data-text="รายการเงินรวม">
	<div class="grid" ng-init="total_b=<%=amtt%>">
	<form action="receiveReport.action" method="post" data-role="validator" data-show-required-state="false" data-hint-mode="line" data-hint-background="bg-red" data-hint-color="fg-white" data-hide-error="5000">
		    <div class="row cells12" ng-init="receive=<%=receiveAmt%>"> 
		     	<div class="cell colspan3">
		    		จำนวนเงินรวม<div class="input-control full-size "> 
		    		<h3 class="no-margin">{{total_b | currency:"฿"}}</h3>
		    		<input type="hidden" id="amtt"ng-model="total_b" value="{{total_b}}"  name="receiveform.amtt" data-validate-func="required" data-validate-hint="This field can not be empty" readonly="readonly" />					</div>
		    	</div> 
		    	<div class="cell colspan3">
		    		จำนวนเงินที่ได้รับ<div class="input-control full-size "> 
					    <input type="number" ng-model="receive" id="receiveAmt" name="receiveAmt" data-validate-func="required" data-validate-hint="This field can not be empty">
						
					</div>
		    	</div> 
		    	<div class="cell colspan3">
		    		จำนวนเงินทอน<div class="input-control full-size "> 
		    		<h3 class="no-margin">{{receive-total_b | currency:"฿"}}</h3>
		    		<input type="hidden" id="torn" name="torn" value="{{receive-total_b}}">
					</div>
		    	</div>  <br>
		    	<div class="cell colspan3"> 
		    		  <input type="hidden" id="docNoHD" name="docNoHD" value="<%=docNo%>">
		    		  <a href="javascript:printRev('<%=docNo%>','<%=splitgprojectcode[0]%>');" class="button warning full-size"><span class="mif-print mif-lg fg-white"></span></a>
					   
				</div>
		    </div>    
		</form>
	 </div>
	</div> <!-- End of example -->
	
  <script src="js/angular.min.js"></script>
  <script type="text/javascript">
 	$(function(){
 		 	$( "#costcode" ).change(function() { 
    		  
			var costcodetext = $("#costcode option:selected").text();
			var splitcostcode = costcodetext.split(" - ");
			$("#hiddencostcode").val(splitcostcode[0]);
 		 	});
 		 	
 		 	
  	}); 
  </script>
  <script type="text/javascript">
  	var app = angular.module('myApp', []);
	app.controller('myCtrl', function($scope) {
	    $scope.n2 = <%=standardprice%>;
	});
  </script>
  
  </body>
</html>
