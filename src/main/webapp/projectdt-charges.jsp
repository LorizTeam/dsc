<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.masterModel.GroupCostCodeMasterModel" %> 
<%@ page import="pcpnru.projectData.*" %>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>ประมาณการค่าใช้จ่าย โครงการ</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/docs.css" rel="stylesheet"> 
	 	<link href="css/style.css" rel="stylesheet">  
	 	
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/angular.min.js"></script>
		<script src="js/app.js"></script>
	</head>
	 
	
	<body ng-app="controllerCalculator" ng-controller="SettingsController">
		 <% String projectcode = (String) request.getParameter("projectcode"); 
		 	String year = (String) request.getParameter("year");
		 	
		 	ProjectData pdb = new ProjectData();
		 	String projectname = pdb.selectProjectname(projectcode);
		 
		 	ProjectDTChargesDB PDTC = new ProjectDTChargesDB();
		 	int percen = PDTC.getPercen(projectcode, year);
		 	double percentage = PDTC.getAmtValue(projectcode, year, percen);
		 	
		 	List SubjobList = PDTC.GetSubjobList();  
		 	List groupcostCodeList = PDTC.GetGroupCostCodeList(projectcode, year);
		 	
		 	String freeze 	= pdb.SelectProjFreeze(projectcode, year);
		 %>
		 <form id="project-chargesdt" action="projectdtcharges.action" method="post">
		 <%@include file="topmenu.jsp" %>
		 <div class="container-fluid" >
			<div class="example"data-text="" >
				<h3 class="align-center margin30">ประมาณค่าใช้จ่าย ของโครงการ <%=projectname%> ปี <%=year%> จำนวนเงินที่ใช้ได้ของ <%=percen%>% ในงบประมาณการรายได้คือ  {{<%=percentage%> | currency:"฿"}}</h3>
				<div class="example" data-text="เพิ่ม">
			        <div class="grid">
			        	<div class="row cells12">
			        		<div class="cell colspan4"> 
					        	กิจกรรม
						        <div class="input-control text full-size">
								    <select id="subjobcode" name="subjobcode">
										    <% 
							        		if (SubjobList != null) {
								        		for (Iterator iterPj = SubjobList.iterator(); iterPj.hasNext();) {
								        			SubjobMasterForm sjInfo = (SubjobMasterForm) iterPj.next();
						      				%>  
								      			<option value="<%=sjInfo.getSubjobCode()%>" >
								       			 	<%=sjInfo.getSubjobCode()%> - <%=sjInfo.getSubjobName()%>
								       			</option>
												<%		} 
													}
												%> 
								    </select>
								</div>
							</div>
							<div class="cell colspan4"> 
					        	กิจกรรมย่อย
						        <div class="input-control text full-size">
								    <select id="childsubjobcode" name="childsubjobcode">
								    	<option value="0000">-- ไม่ระบุ --</option>
								    </select>
								</div>
							</div>  
			        	</div>
					  	<div class="row cells12">
					        <div class="cell colspan4"> 
					        	รายการ
					        	<div class="input-control text full-size ">  
			                    	<input type="hidden" id="projectcode" name="projectcode" value="<%=projectcode%>"> 
			                    	<input type="hidden" id="year" name="year" value="<%=year%>">
	                        		<input type="hidden" id="gcostcode" name="gcostcode" >
	                        		<input type="hidden" id="arnumber" name="arnumber" > 
					                
								<input type="text" id="gcostname" name="gcostname" ng-model="name" readonly="readonly" >
							    <div class="button-group">
							    <button class="button mini-button" type="button" onclick="deleteCC();"><span class="mif-bin"></span></button>
							    <button class="button mini-button" type="button" onclick="javascript:getGcostcode('<%=projectcode%>','<%=year%>');"> <span class="mif-search"></span></button>
								</div> 
			                    </div>
							</div> 
							<div class="cell ">คำนวน<br>
					        	 <button type="button" class="button primary mif-calculator2" onclick="showCharm('right')"></button> 
							</div>
							
							<div class="cell colspan2"> 
					        	จำนวนเงิน
					        	<div class="input-control text full-size"> 
			                    	<input id="budget" name="budget" onblur="CommaBudget()">
			                    </div>
							</div> 
							<%if(freeze.equals("N")){%>
							<div class="cell colspan5"><br>
								 <button class="button success" type="submit" name="add">เพิ่มประมาณการรายได้</button>
					    	</div>
					    	<%} %>
					</div>
				</div>  
			</div>
				  
				<div class="grid ">	
					<div class="window ">
						<div class="row cells12 align-center  window-caption bg-cyan fg-white" >
					  		<div class="cell colspan7">
					  			รายการ
					  		</div>
					  		<div class="cell colspan4">
					  			จำนวนเงิน
					  		</div>
					  	</div> 
				  	</div>
				 <!-- รายรับ -->	
					<div class="example" data-text="ค่าใช้จ่าย">
					  <!-- subjob -->
					 <%
					 ProjectData pjdata = new ProjectData();
					double pjdt_requisitiontotal = 0;
					double pjdt_receivetotal = 0;
				  	List projectDTListRequisition_subjob = pjdata.GetProjectDTDetailList(projectcode, year, "", "", "", "",
				  			"", "", "", "", "", "asc", "", "a.subjob_code");
				  	
				  	if(projectDTListRequisition_subjob != null){
				  		Iterator projectDTIter_subjob = projectDTListRequisition_subjob.iterator();
				  		while(projectDTIter_subjob.hasNext()){
				  			ProjectModel pjmodel = (ProjectModel) projectDTIter_subjob.next();
				  			pjdt_receivetotal += Float.parseFloat(pjmodel.getBudget());
				  	%>
				  		<div class="row cells12 " >			  
						  	<h5 class="cell colspan8 subjob">
						  		<%=pjmodel.getSubjob_name() %>
						  	</h5>
						  	<div class="cell colspan4 align-center">
						  		
						  	</div>
						  </div>
						  <!-- child_subjob -->
						  <%
						  List projectDTListRequisition_childsubjob = pjdata.GetProjectDTDetailList(projectcode, year, "", pjmodel.getSubjob_code(), "", "",
						  			"", "", "", "", "", "asc", "", "a.childsubjobcode");
						  	
						  	if(projectDTListRequisition_childsubjob != null){
						  		Iterator projectDTIter_childsubjob = projectDTListRequisition_childsubjob.iterator();
						  		while(projectDTIter_childsubjob.hasNext()){
						  			ProjectModel pjmodel_childsubjob = (ProjectModel) projectDTIter_childsubjob.next();
						  			
					  				if(!pjmodel_childsubjob.getChildsubjobcode().equals("000")){
					  	  %>
					  	  				<div class="row cells12 " >			  
										  	<h5 class="cell colspan8 child-subjob">
										  		<%=pjmodel_childsubjob.getChildsubjobname() %>	
										  	</h5>
										  	<div class="cell colspan4 align-center">
										  		
										  	</div>
										  </div>
					  	  
					  	  <%
					  				}
						  %>
							  
							  		<!-- gcostcode -->
							  		<%
							  		
									  List projectDTListRequisition_gcostcode = pjdata.GetProjectDTDetailList(projectcode, year, "", "", "", pjmodel_childsubjob.getChildsubjobcode(),
									  			"", "", "", "", "", "asc", "", "");
									  	
									  	if(projectDTListRequisition_gcostcode != null){
									  		Iterator projectDTIter_gcostcode = projectDTListRequisition_gcostcode.iterator();
									  		while(projectDTIter_gcostcode.hasNext()){
									  			ProjectModel pjmodel_gcostcode = (ProjectModel) projectDTIter_gcostcode.next();
									  			pjdt_requisitiontotal += Float.parseFloat(pjmodel_gcostcode.getBudget());
									  			
									  %>
									  		<div class="row cells12 " >			  
											  	<p class="cell colspan7 costcode">
											  		<%=pjmodel_gcostcode.getGcostcode_name() %>	
											  	</p>
											  	<span class="hdsubjobcode"><input type="hidden" id="subjobhd" name="subjobhd" value="<%=pjmodel_gcostcode.getSubjob_code()%>"></span>
											  	<span class="hdcsubjobcode"><input type="hidden" id="csubjobhd" name="csubjobhd" value="<%=pjmodel_gcostcode.getChildsubjobcode()%>"></span>
											  	<span class="hdgcostcode"><input type="hidden" id="hdgcostcode" name="hdgcostcode" value="<%=pjmodel_gcostcode.getGcostcode()%>"></span>
											  	<div class="cell colspan4 align-center">
											  		{{ <%=pjmodel_gcostcode.getBudget() %> | currency:"฿"}}
											  	</div>
											  	<div class="cell">
										  			<a href=""><span class="mif-cross deletebt"></span></a> 
										  		</div>
											  </div>
									  
									  <%
									  		}
									  	}
									  %>
							  		<!-- gcostcode -->
						  <%
						
						  		}
						  	}
						  %>
						  <!-- child_subjob -->
						  
				  	<%
				  		}
				  	}
					
					%>
					<!-- subjob -->
					
					<!-- ------------------------------------------------------ -->
					
					
					  <!--Totle subjob -->  
					   <div class="row cells12 " >			  
					  	<div class="cell colspan7 align-right">
					  		<h4>รวม</h4>
					  	</div>
					  	<div class="cell colspan4 align-center">
					  		<h4>{{<%=pjdt_requisitiontotal %>| currency:"฿"}}</h4>
					  	</div>
					  </div>
					  <!--Totle subjob -->
					  <!--Totle Balance -->  
					   <div class="row cells12 " >			  
					  	<div class="cell colspan7 align-right">
					  		<h4>คงเหลือ</h4>
					  	</div>
					  	<div class="cell colspan4 align-center">
					  		<% double Balance = 0;
					  		   Balance = percentage-pjdt_requisitiontotal; %>
					  		<h4>{{<%=Balance %>| currency:"฿"}}</h4>
					  		<input type="hidden" id="balance" name="balance" value="<%=Balance%>">
					  	</div>
					  </div>
					  <!--Totle Balance -->
					</div> 
				<!-- รายรับ -->		
				</div>
				<div class="row " >	
					<div class="cell align-center">
						<a href="projectdt.jsp?projectcode=<%=projectcode%>&year=<%=year%>" class="button">กลับ</a>
					</div>
				</div>
			</div>
		</div>
		
		<div  class="charm right-side bg-gray" data-role="charm" data-position="right" id="right-charm" style="max-width:50%">
				<span class="charm-closer"></span>
				<h3 class="text-light">เพิ่มการคำนวน</h3>
				<div class="grid">
					<div class="row cells2 example bg-gray">
						<div class="cell">จำนวน
				        	<div class="input-control text full-size"> 
		                    	<input type="number" step="0.01" name="qty" ng-model="value1" >
		                    </div>
						</div> 
						<div class="cell"> 
				        	หน่วยนับ
					        <div class="input-control text full-size">
							    <select onchange="" name="unit" class="align-center" ng-model="type1">
							        <option value="">โปรดเลือก</option>
							        <% 
							        UnitMasterDB unitM = new UnitMasterDB();
									List<UnitMasterForm> unitMasterList = unitM.GetUnitMasterList("");
							        
					        		if (unitMasterList != null) {
						        		for (UnitMasterForm unitjMaster:unitMasterList) {
				      				%>  
					      			<option value="<%=unitjMaster.getUnit_name()%>" ><%=unitjMaster.getUnit_name()%></option>
									<%		} 
										}
									%>
							    </select>
							</div>
						</div> 
					</div>
					
					
			<!-- เพิ่ม operation -->
			<div ng-repeat="cal in calculator "class="example bg-gray">
					<div class="row cells4" >
						<div class="cell">
					        <div class="input-control text full-size ">
							    <select class="align-center" name="aroperation" ng-model="cal.operation" id="select_{{$index}}">
							        <option>+</option>
							        <option>-</option>
							        <option>*</option>
							        <option>/</option> 
							    </select>
							</div>
							
						</div>
						<div class="cell colspan3 align-right">
						      <button class="button danger" ng-click="removeContact(cal)">X</button>
					      </div> 
					</div>
					<div class="row cells2">
						<div class="cell">จำนวน
				        	<div class="input-control text full-size"> 
		                    	<input type="number" step="0.01" name="arqty" ng-model="cal.value" aria-labelledby="select_{{$index}}">
		                    </div>
						</div> 
						<div class="cell"> 
				        	หน่วยนับ
					        <div class="input-control text full-size">
							    <select onchange="" name="arunit" class="align-center" ng-model="cal.type" id="select_{{$index}}">
							        <option value="">โปรดเลือก</option>
							        <%  
							    	unitMasterList = unitM.GetUnitMasterList("");
							        
					        		if (unitMasterList != null) {
					        			for (UnitMasterForm unitjMaster:unitMasterList) {
						      		%>  
							      			<option value="<%=unitjMaster.getUnit_name()%>" ><%=unitjMaster.getUnit_name()%></option>
									<%		} 
										}
									%>
							    </select>
							</div>
							
						</div>
						 
					</div>
					
			<!-- เพิ่ม operation -->
				</div>
				<div class="row" >
						<div class="cell align-center">
							<a href="" class="button primary"ng-click="addContact()">เพิ่มตัวคำนวน</a>
						</div>
					</div>
				</div>
				<div class="example bg-gray">
					<div class="row"  >
						{{name+' '+value1+' '+type1+' '}}
						 <span ng-repeat="cal in calculator">
						   {{ cal.operation+' '+cal.value+' '+cal.type+' ' }}
						 </span>
					</div>
				</div>
				<%if(freeze.equals("N")){%>
				  <div class="row"  >
					 <div class="cell align-center"><br>
							  <button class="button success" type="submit" name="add">เพิ่มประมาณการรายได้</button> 
					</div>
				</div>
				<%} %>
			</div>
		</form>
		 
		<script>
		function CommaBudget() {
   			var budget = $("#budget").val(); 
   			var t1 = "";
   			if(budget == "NaN"){
   				t1 = "0";
   			}else if(budget == ""){
   				t1 = "0";
   			}
   			else{
   				budget = budget.replace(/,/g,"");
   		    var t1 = parseFloat(budget).toLocaleString("en-US");
   			} 
   			$("#budget").val(t1);
   		}
		
		function getGcostcode(projectcode, year) {
			var load = window.open('/pcpnru/window-groupcostcode-requisition.jsp?projectcode='+projectcode+'&year='+year+' ','requisition',
			             'scrollbars=yes,menubar=no,height=600,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
		}
		function deleteCC() {
			$("#gcostcode").val("");
			$("#gcostname").val("");
		}  
		
		function showCharm(id){
            var  charm = $("#right-charm").data("charm");
            if (charm.element.data("opened") === true) {
                charm.close();
            } else {
                charm.open();
            }
        }
		
		$(function(){
			  
			// load
			var subjobcode = $("#subjobcode").val();
			var out = ''; 
			$.ajax({  // select history
			  	 
		          type: "post",
		          url: "ajax_projectdt-charges.jsp", //this is my servlet 
		          data: {subjobcode:subjobcode},
		          async:false, 
		          success: function(result){
		          
		          obj = JSON.parse(result);
		          	// alert(obj)
		          	
		          	var value = obj;
		          if( Object.keys(obj).length === 0){
		        	  $("#childsubjobcode").html('<option value="">-- ไม่ระบุ --</option>');
		          }else{
			          for(var i = 0 ; i < obj.length; i++){
							out +=  
							'<option value="'+obj[i].childsubjobcode+'">'+
							   obj[i].childsubjobcode+' - '+obj[i].childsubjobname+
							'</option>'; 
						}  
						$("#childsubjobcode").html(out);
			          }
		          }
		       }); 
			// load
			 
			// change dropdown
			$( "#subjobcode" ).change(function() {
				var subjobcode = $("#subjobcode").val();
				var out = '';
				
				$.ajax({  // select history
				  	 
		          type: "post",
		          url: "ajax_projectdt-charges.jsp", //this is my servlet 
		          data: {subjobcode:subjobcode},
		          async:false, 
		          success: function(result){
		          
		          obj = JSON.parse(result);
		          	// alert(obj)
		          	
		          	var value = obj;
		          if( Object.keys(obj).length === 0){
		        	  $("#childsubjobcode").html('<option value="">-- ไม่ระบุ --</option>');
		          }else{
			          for(var i = 0 ; i < obj.length; i++){
							out +=  
							'<option value="'+obj[i].childsubjobcode+'">'+
							   obj[i].childsubjobcode+' - '+obj[i].childsubjobname+
							'</option>'; 
						}  
						$("#childsubjobcode").html(out);
			          }
		          }
		       }); 
			});
			// change dropdown
			
			$('.deletebt').click(function () {   
				var index = $(".deletebt").index(this);
			//	var cc = $(".gcostcodehd > #gcostcodehd").eq(index).val();
			//	var cc = $("#gcostcodehd").eq(index).val();
			//	alert(cc);
				$("#arnumber").val(index);
				$("#gcostcode").val($(".hdgcostcode > #hdgcostcode").eq(index).val()); 
		    	$("#project-chargesdt").submit();
			  
			});
			
			$("#gcostname").change(function () { 
   				var text = $("#gcostname :selected").text();
   				var text1 = text.split(" - "); 
   				text = text1[0];  
   				$("#gcostcode").val(text); 
   			}); 
		});  
		 
		</script>
		
	</body>
</html>
