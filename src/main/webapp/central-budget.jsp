<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
  <head>
    <base href="<%=basePath%>">
    
    <title>งบกลาง</title>
    
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
		<link href="css/docs.css" rel="stylesheet"> 
	 	<link href="css/select2.css" rel="stylesheet">
	 	<link href="css/bootstrap-datepicker3.css" rel="stylesheet">
	 	<link href="css/jquery.dataTables.min.css" rel="stylesheet">
	 	
		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/docs.js"></script>
 		<script src="js/jquery.dataTables.min.js"></script>     
		<script src="js/select2.js"></script>
		<script src="js/bootstrap-datepicker-th.js"></script>
		<script src="js/angular.min.js"></script>
		<script src="js/central-budget.js"></script>
  </head>
  
  <body ng-app="central-budget1" ng-controller="myCtrl">
  	
    <div><%@include file="topmenu.jsp" %></div>
	<br>
	<form id="central-budget" action="centralBudget.action" method="post" >
		<div class="example" data-text="โครงการที่ต้องการโยก">
			<div class="flex-grid">
			  	<div class="row flex-just-left">
			        <div class="cell colspan2 "> 
			       		<h4 class="align-right">งบกลางประจำปี&nbsp;<s:property value="centralBudgetForm.year" /> </h4>
			       		<s:hidden id="year" name="centralBudgetForm.year" />
			    	</div>
			    	<div class="cell colspan1"> 
			       		 <h4 class="align-right"> 
				       		จำนวนเงิน&nbsp; 
						 </h4> 
			    	</div>
			    	<div class="cell colspan1 "> 
			       		<h4><small class="input-control full-size"> 
			       			<s:textfield dir="rtl" id="amt" name="centralBudgetForm.amt" />
			       		</small></h4>
			    	</div> 
			    	<div class="cell colspan1 "> 
			       		<h4 class="align-right">วันที่&nbsp;</h4>
			    	</div>
			    	<div class="cell colspan1"> 
			    		<h4>
			    		<small class="input-control full-size"> 
						    <input id="day" name="centralBudgetForm.docdate" ng-model="day" required/>
						</small>
						</h4>
			    	</div>
			    	<div class="cell colspan6 "> 
			       		<h4 class="align-right">เลขที่เอกสาร&nbsp;<s:property value="centralBudgetForm.docno" /> </h4>
			    		<s:hidden id="docno" name="centralBudgetForm.docno" />
			    		<s:hidden id="g1" name="g1" />
			    		<s:hidden id="g2" name="g2" /> 
			    	</div> 
			    </div>
			</div> 
		
		</div> <!-- End of example --> 
		
		<div class="example" data-text="รายการโยก"> 
			<div class="flex-grid">
			  	<div class="row flex-just-left">
			        <div class="cell colspan1 "> 
			       		<h4 class="align-right">โครงการ&nbsp;</h4>
			    	</div>
			    	<div class="cell colspan6"> 
			       		 <h4><small class="input-control full-size" > 
				       		 <select id="projectcode_central" ng-change="projectcentral()" ng-model="project_central" name="centralBudgetForm.project_code" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ">
							   <option value="">กรุณาเลือกโครงการ</option>
							   <% 
							   List projectCentralList = (List) request.getAttribute("projectCentralList");
				        		if (projectCentralList != null) {
				        			 
					        		for (Iterator iterPj = projectCentralList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next(); 
					        			String p1 = pjModel.getProject_code();
						        	%> 
						        		<option value="<%=pjModel.getProject_code()%> - <%=pjModel.getYear()%>" >
							       			 <%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%> - ปี <%=pjModel.getYear() %>
							       		</option>
						        	<%
						        			
			      						} 
									}
								%>
					   		</select>
						</small></h4>
			    	</div>
			 </div>
			 </div>
			<div class="flex-grid">
			  	<div class="row flex-just-left">
			        <div class="cell colspan1"> 
			       		<h4 align="right">ค่าใช้จ่าย&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan6">
			    		<h4><small class="input-control full-size"> 
			    			<select name="centralBudgetForm.gcostcode" ng-change="central_amt()" ng-model="gcostcode" id="gcostcode" required>
			    				<option value="">-- please Select --</option>
						    	<option ng-repeat="option in datas" value="{{option.gcostcode}}">{{option.gcostcode_name}}</option> 
						   	</select>
						    
						</small></h4> 
			    	</div>
			    	<div class="cell colspan1"> 
			       		<h4 align="right">เงินคงเหลือ&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan1" > 
			    		<h4><small class="input-control full-size"> 
			       			<s:textfield dir="rtl" id="frombalance" name="centralBudgetForm.frombalance" value="{{frombalance}}" readonly="true" />
			       		</small></h4>
			    	</div> 
				</div>
				
				<div class="row flex-just-left">
			        <div class="cell colspan2"> 
			       		<h4 align="right">จำนวนเงินที่ต้องการโยกงบ&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan1">
			    		<h4><small class="input-control full-size">
			    			<input type="text" dir="rtl" id="rocking_budget" name="centralBudgetForm.rocking_budget" value="{{rocking_budget}}" required>			
			    		</small></h4>		 
			    	</div> 
			    	<div class="cell colspan1"> 
			       		<h4 align="right">คงเหลือ&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan1">
			    		<h4><small class="input-control full-size">
			    			<input type="text" dir="rtl" id="balance" name="centralBudgetForm.balance" value="{{balance}}" readonly="readonly">			
			    		</small></h4>		 
			    	</div> 
			    	<div class="cell colspan1"> 
			       		<h4 align="right">หมายเหตุ&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan6">
			    		<h4><small class="input-control full-size">
			    			<input type="text" id="remark" name="centralBudgetForm.remark" value="{{ remark }}" >			
			    		</small></h4>		 
			    	</div> 
				</div> 
			</div>  
		
			<div class="flex-grid">
			  	<div class="row flex-just-center" >
			    	<div class="cell colspan12" align="center">
						<button class="button success" type="submit" name="add" >บันทึกการโยกงบประมาณ</button>
						 
					</div> 
			    </div>
			</div> 
		
		</div> <!-- End of example --> 
		
	</form>
	<div class="example" data-text="รายการ">
            <table id="table_rocking"  class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                    <th>ลำดับ</th>
                    <th>โครงการ</th> 
                    <th>ค่าใช้จ่าย</th> 
                    <th>จำนวนเงินที่โยก</th>
                    <th>เงินคงเหลือ</th> 
                    <th>งบกลางคงเหลือ</th>
                    <th> </th>
                </tr>
                </thead>  
                <tbody>
                <%		 
                		List CentralBudgetList = (List) request.getAttribute("CentralBudgetList");
                		if (CentralBudgetList != null) {
						int x = 0;
						for (Iterator iter = CentralBudgetList.iterator(); iter.hasNext();) {
						x++; 
						CentralBudgetForm rbgform = (CentralBudgetForm) iter.next();
						 
				%>
                <tr>  
                    <td align="center"><%=x%></td>
                    <td class="tdpj" align="left"><%=rbgform.getProject_code()%> - <%=rbgform.getProject_name()%></td>
                    <td class="tdgcostcode_rock" align="left"><%=rbgform.getGcostcode()%> - <%=rbgform.getGcostname()%></td>
                    <td class="" align="right">{{<%=rbgform.getRocking_budget()%> | currency:'฿'}}</td> 
                    <td class="" align="right">{{<%=rbgform.getBalance()%> | currency:'฿'}}</td>
                    <td class="" align="right">{{<%=Float.parseFloat(rbgform.getAmt())-Float.parseFloat(rbgform.getRocking_budget())%> | currency:'฿'}}</td>
                    <td class="" align="center"><div class="cell"><a href=""><span class="mif-cross deletebt"></span></a></div></td>
                </tr>	 
                 <% 	} %>
                
                <%} %>
                </tbody>
            </table>
        </div> <!-- End of example table --> 
    <script type="text/javascript">
		$(function(){
			
			$("#projectcode_central").select2();
			$("#gcostcode").select2(); 
			
			$('#table_rocking').DataTable( {
	          	scrollY:  '35vh',
	          	scrollX: true,
	          	scrollCollapse: true,
	          	ordering: false,
	          	"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
	      	} );
			
			$("#rocking_budget").blur(function (){
	   			var amount = $("#rocking_budget").val(); 
	   			var t1 = "";
	   			if(amount == "NaN"){
	   				t1 = "";
	   			}else if(amount == ""){
	   				t1 = "";
	   			}
	   			else{
	   			amount = amount.replace(/,/g,"");
	   		    var t1 = parseFloat(amount).toLocaleString("en-US");
	   			} 
	   			$("#rocking_budget").val(t1);
	   			
	   			var fb = $("#frombalance").val();
	   			fb = fb.replace(/,/g,"").replace("฿","");
	   			
	   			var rgb = $("#rocking_budget").val();
	   			rgb = rgb.replace(/,/g,"");
	   			
	   			var amt = $("#amt").val();
	   			amt = amt.replace(/,/g,"");
	   			
	   			if(parseFloat(amt)<parseFloat(rgb)){
	   				$("#balance").val("");  
	   			}else if(rgb==''){
	   				$("#balance").val(""); 
	   			}else if(rgb=='0'){
	   				$("#balance").val(""); 
	   			}else{
	   			
	   				var balance = parseFloat(fb)+parseFloat(rgb);
		   			 
		   			balance = parseFloat(balance).toLocaleString("en-US");
		   			$("#balance").val(balance);  
	   			}
	   			
	   		});
			$("#rocking_budget").keyup(function (){ 
	   			
				var fb = $("#frombalance_rock").val();
	   			fb = fb.replace(/,/g,"").replace("฿","");
	   			
	   			var rgb = $("#rocking_budget").val();
	   			rgb = rgb.replace(/,/g,"");
	   			
	   			if(parseFloat(fb)<parseFloat(rgb)){
	   				 
	   				$("#balance").val("");  
	   			}else if(rgb==''){
	   				 
	   				$("#balance").val(""); 
	   			}else{
	   				 
	   				var balance = fb-rgb;
	   				
		   			balance = parseFloat(balance).toLocaleString("en-US");
		   			$("#balance").val(balance);  
	   			}
	   			
	   		}); 
			
			$('.deletebt').click(function () {  
	        	 
        		var index = $(".deletebt").index(this);
        		  
        		var forsplit1 = $(".tdpj").eq(index).text().split(" - "); 
        		var td1 =  forsplit1[0];
        		 
        		var forsplit2 = $(".tdgcostcode_rock").eq(index).text().split(" - "); 
        		var td2 =  forsplit2[0];
        		
        		$("#g1").val(td1); 
        		$("#g2").val(td2);  
        		
		    	$("#central-budget").submit();
        	  
    	}); 
			
			$("#day").datepicker({
			    format: "dd/mm/yyyy",
		        todayBtn: true,
		        clearBtn: true,
		        autoclose: true,
		        todayHighlight: true
		    });
		});
	</script>
	 
	
  </body>
</html>
