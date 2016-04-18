<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	String username = "", project_code = "";
	
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "014";
		
		CheckAuthenPageDB capDB = new CheckAuthenPageDB();
		
		chkAuthen = capDB.getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}else{
			project_code = capDB.getProjectCode(username);
		}
	} 
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
  <head>
    <base href="<%=basePath%>">
    
    <title>หน้าโยกงบประมาณ</title>
    
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
		<script src="js/rocking-budget.js"></script>
  </head>
  
  <body ng-app="rocking-budget" ng-controller="myCtrl">
  	
    <div><%@include file="topmenu.jsp" %></div>
	<br>
	<form id="rocking-budget" action="rockingBudget.action" method="post" name="rockbudgetform">
		<div class="example" data-text="โครงการที่ต้องการโยก">
			<div class="flex-grid">
			  	<div class="row flex-just-left">
			        <div class="cell colspan1 "> 
			       		<h4 class="align-right">โครงการ&nbsp;</h4>
			    	</div>
			    	<div class="cell colspan6"> 
			       		 <h4><small class="input-control full-size" > 
				       		 <select id="project_code" ng-change="projectchange()" ng-model="project" name="rockingBudgetForm.project_code" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ">
							   <option value="">กรุณาเลือกโครงการ</option>
							   <% 
							   		List projectMasterList = (List) request.getAttribute("projectMasterList");
				        		if (projectMasterList != null) {
				        			 
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
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
			    	<div class="cell colspan1 "> 
			       		<h4 class="align-right">วันที่&nbsp;</h4>
			    	</div>
			    	<div class="cell colspan1"> 
			    		<h4>
			    		<small class="input-control full-size"> 
						    <input type="text" id="day" name="rockingBudgetForm.docdate" ng-model="day" required/>
						    <span class="input-state-success mif-checkmark"></span>
						</small>
						</h4>
			    	</div>
			    	<div class="cell colspan1 "> 
			       		<h4 class="align-right">เลขที่เอกสาร&nbsp;</h4>
			    	</div>
			    	<div class="cell colspan2">  
		    		<div class="input-control full-size"> 
					    <h4 class="sub-header fg-black"><s:property value="rockingBudgetForm.docno"/></h4>
					    <s:hidden id="docno" name="rockingBudgetForm.docno" />
					    <s:hidden id="year" name="rockingBudgetForm.year" />
					    <s:hidden id="g1" name="g1" value="{{ g1 }}" />
					    <s:hidden id="g2" name="g2" value="{{ g2 }}" />
					</div>
					
		    	</div>
			    </div>
			</div> 
			<div class="flex-grid" >
			  	<div class="row flex-just-left">
			        <div class="cell colspan1"> 
			       		<h4 align="right">รับงบ&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan6">
			    		<h4><small class="input-control full-size">
			    			<% List groupcostCodeList = (List) request.getAttribute("groupcostCodeList");
			    			
			    			if (groupcostCodeList != null) {
			    				Iterator costcodeIterate = groupcostCodeList.iterator();
			        			while(costcodeIterate.hasNext()){
			        				GroupCostCodeMasterModel gccInfo = (GroupCostCodeMasterModel) costcodeIterate.next(); 
					        				
					        	%>
					        		<select name="rockingBudgetForm.gcostcode" id="gcostcode">
					        			<option value="<%=gccInfo.getCostCode()%>" ><%=gccInfo.getCostName()%></option>
						       		</select>
					        	<%
					        			
		      						} 
								}else{
							%>
			    			<select name="rockingBudgetForm.gcostcode" ng-model="gcostcode" id="gcostcode" ng-change="gcostcodechange()" required>
			    				<option value="">-- please Select --</option>
						    	<option ng-repeat="option in datas" value="{{option.gcostcode}}">{{option.gcostcode_name}}</option> 
						   	</select>
						   	<%} %>
						</small></h4> 
			    	</div>
			    	<div class="cell colspan1"> 
			       		<h4 align="right">คงเหลือ ยกมา</h4> 	  
			    	</div> 
			    	<div class="cell colspan1" ng-init="frombalance=<%=request.getAttribute("frombalance")%>"> 
			    		<h4><small class="input-control full-size"> 
			       			<s:textfield dir="rtl" id="frombalance" name="rockingBudgetForm.frombalance" value="{{ frombalance | currency:'฿' }}" />
			       		</small></h4>
			    	</div>  
				</div>
			</div>   
		
		</div> <!-- End of example --> 
		
		<div class="example" data-text="รายการโยก"> 
			
			<div class="flex-grid">
			  	<div class="row flex-just-left">
			        <div class="cell colspan1"> 
			       		<h4 align="right">จัดสรรงบ&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan6">
			    		<h4><small class="input-control full-size">
			    			<% List groupcostCode2List = (List) request.getAttribute("groupcostCode2List");
			    			
			    			if (groupcostCode2List != null) {
			    				Iterator Iterate = groupcostCode2List.iterator();
			    				%>
			    				<select name="rockingBudgetForm.gcostcode_rock" id="gcostcode_rock" ng-model="gcostcode_rock" ng-change="gcostcoderockchange()">
			    				<option selected value="">-- please Select --</option>
			    				<%
			        			while(Iterate.hasNext()){
			        				GroupCostCodeMasterModel gcc1Info = (GroupCostCodeMasterModel) Iterate.next(); 
					        				
					        	%> 
					        		<option value="<%=gcc1Info.getCostCode()%>" ><%=gcc1Info.getCostName()%></option> 
					        	<% 	
		      						} 
			    				%>
			    				</select>
							<%	}else{
							%>
			    			<select name="rockingBudgetForm.gcostcode_rock" ng-model="gcostcode_rock" id="gcostcode_rock" ng-change="gcostcoderockchange()" required>
			    				<option value="">-- please Select --</option>
						    	<option ng-repeat="option in datas_rock" value="{{option.gcostcode}}">{{option.gcostcode_name}}</option> 
						   	</select>
						   	<%} %>
						</small></h4> 
			    	</div>
			    	<div class="cell colspan1"> 
			       		<h4 align="right">คงเหลือ ยกมา</h4> 	  
			    	</div> 
			    	<div class="cell colspan1" ng-init="frombalance_rock=0"> 
			    		<h4><small class="input-control full-size"> 
			       			<s:textfield dir="rtl" id="frombalance_rock" name="rockingBudgetForm.frombalance_rock" value="{{ frombalance_rock | currency:'฿' }}" />
			       		</small></h4>
			    	</div>
				</div>
				
				<div class="row flex-just-left">
			        <div class="cell colspan2"> 
			       		<h4 align="right">จำนวนเงินที่ต้องการโยกงบ&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan1">
			    		<h4><small class="input-control full-size">
			    			<input type="text" dir="rtl" id="rocking_budget" name="rockingBudgetForm.rocking_budget" value="{{ rocking_budget }}" required>			
			    		</small></h4>		 
			    	</div> 
			    	<div class="cell colspan1"> 
			       		<h4 align="right">คงเหลือ&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan1">
			    		<h4><small class="input-control full-size">
			    			<input type="text" dir="rtl" id="balance" name="rockingBudgetForm.balance" value="{{ balance }}" readonly="readonly">			
			    		</small></h4>		 
			    	</div> 
			    	<div class="cell colspan1"> 
			       		<h4 align="right">หมายเหตุ&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan6">
			    		<h4><small class="input-control full-size">
			    			<input type="text" id="remark" name="rockingBudgetForm.remark" value="{{ remark }}" >			
			    		</small></h4>		 
			    	</div> 
				</div> 
			</div>  
		
			<div class="flex-grid">
			  	<div class="row flex-just-center" >
			    	<div class="cell colspan12" align="center">
						<button class="button success" type="submit" name="add" ng-click="rockbudgetform.$valid && addrequisition()" >บันทึกการโยกงบประมาณ</button>
						 
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
                    <th>ค่าใช้จ่าย</th> 
                    <th>คงเหลือ</th>
                    <th>ค่าใช้จ่าย ที่โยก</th>
                    <th>คงเหลือ</th>
                    <th>จำนวนเงินที่โยก</th>
                    <th>เงินคงเหลือ</th> 
                    <th> </th>
                </tr>
                </thead>  
                <tbody>
                <%		 
                		List RockingBudgetList = (List) request.getAttribute("RockingBudgetList");
                		if (RockingBudgetList != null) {
						int x = 0;
						for (Iterator iter = RockingBudgetList.iterator(); iter.hasNext();) {
						x++; 
						RockingBudgetForm rbgform = (RockingBudgetForm) iter.next();
				%>
                <tr>  
                    <td align="center"><%=x%></td>
                    <td class="tdgcostcode" align="left"><%=rbgform.getGcostcode()%> - <%=rbgform.getGcostname()%></td>
                    <td class="" align="right"><%=rbgform.getFrombalance()%></td>  
                    <td class="tdgcostcode_rock" align="left"><%=rbgform.getGcostcode_rock()%> - <%=rbgform.getGcostname_rock()%></td>
                    <td class="" align="right"><%=rbgform.getFrombalance_rock()%></td>  
                    <td class="" align="right"><%=rbgform.getRocking_budget()%></td>
                    <td class="" align="right"><%=rbgform.getBalance()%></td> 
                    <td class="" align="center"><div class="cell"><a href=""><span class="mif-cross deletebt"></span></a></div></td>
                </tr>	 
                 <% 	} %>
                
                <%} %>
                </tbody>
            </table>
        </div> <!-- End of example table --> 
    <script type="text/javascript">
		$(function(){
			 
			$("#gcostcode").select2();
			$("#gcostcode_rock").select2();
			
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
	   			
	   			var fb = $("#frombalance_rock").val();
	   			fb = fb.replace(/,/g,"").replace("฿","");
	   			
	   			var rgb = $("#rocking_budget").val();
	   			rgb = rgb.replace(/,/g,"");
	   			
	   			if(parseFloat(fb)<parseFloat(rgb)){
	   				$("#balance").val("");  
	   			}else if(rgb==''){
	   				$("#balance").val(""); 
	   			}else if(rgb=='0'){
	   				$("#balance").val(""); 
	   			}else{
	   			
	   				var balance = fb-rgb;
		   			 
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
        		  
        		var forsplit1 = $(".tdgcostcode").eq(index).text().split(" - "); 
        		var td1 =  forsplit1[0];
        		
        		var forsplit2 = $(".tdgcostcode_rock").eq(index).text().split(" - "); 
        		var td2 =  forsplit2[0];
        		
        		$("#g1").val(td1); 
        		$("#g2").val(td2); 
        		
		    	$("#rocking-budget").submit();
        	  
    	}); 
			
			$("#day").datepicker({
			    format: "dd-mm-yyyy",
			    todayBtn: "linked",
		        clearBtn: true,
		        autoclose: true,
		        todayHighlight: true
		    });
		});
	</script>
	 
	
  </body>
</html>
