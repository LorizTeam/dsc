<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.masterModel.GroupCostCodeMasterModel" %> 
<%@ page import="pcpnru.projectData.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		
		<title>ประมาณการรายได้ รายจ่าย โครงการ</title>
		
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
	    <script src="js/docs.js"></script>
  		<script src="js/angular.min.js"></script>
		<script src="js/app.js"></script>
	</head>

	<body ng-app="controllerCalculator" ng-controller="SettingsController">
		 <% 
		 	String projectcode = (String) request.getParameter("projectcode"); 
		 	if(projectcode==null) projectcode = (String) request.getAttribute("project_code");
		 	String year = (String) request.getParameter("year");
		 	if(year==null) year = (String) request.getAttribute("year");
		 	
		 	ProjectData pdb = new ProjectData();
		 	String projectname = pdb.selectProjectname(projectcode);
		 	double target = pdb.getTarget(projectcode, year);
		 	
		 	ProjectDTReceiveDB PDTR = new ProjectDTReceiveDB();
		 	List childSubjobList = PDTR.GetChildSubjobList();
		 	List groupcostCodeList = PDTR.GetGroupCostCodeList(projectcode, year);
		 	
		 	String freeze 	= pdb.SelectProjFreeze(projectcode, year);
		 %>
		 		<%@include file="topmenu.jsp" %>
		 <form id="project-receivedt-pcc" action="projectdtreceivepcc.action" method="post">
		 <div class="container-fluid" >
		 	

			<div class="example"data-text="" >
			<h3 class="align-center margin30">ประมาณการรายได้ ของโครงการ <%=projectname%> ปี <%=year%></h3>
			
			
			<div class="example" data-text="เพิ่ม">
	         <div class="grid">
			  	<div class="row cells12">
			        
			        <div class="cell colspan5"> 
			        	กิจกรรม
			        	 <div class="input-control text full-size"> 
			        	 <select required id="csubjob" name="csubjob" >
					   	 <option value="">-- ไม่ระบุ --</option>
						    <% 
			        		if (childSubjobList != null) {
				        		for (Iterator iterPj = childSubjobList.iterator(); iterPj.hasNext();) {
				        			ChildSubjobMasterForm csjInfo = (ChildSubjobMasterForm) iterPj.next();
		      				%>  
				      			<option value="<%=csjInfo.getChildsubjobcode()%>" >
				       			 	<%=csjInfo.getChildsubjobcode()%> - <%=csjInfo.getChildsubjobname()%>
				       			</option>
								<%		} 
									}
								%>
					   </select>
					    <input type="hidden" name="year" value="<%=year%>" /><input type="hidden" name="project_codehd" id="project_codehd" />
	                     </div>
					</div> 
			    </div>
			    <div class="row cells12">
	         		<div class="cell colspan5">
	         		 โครงการ
				        <div class="input-control text full-size">
						    <select id="project_code" name="project_code" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ" >
							   <option value="" >กรุณาเลือกโครงการ</option>
							    <%  
							   	List projectMasterList = null;
							    
							    ProjectMasterDB projM = new ProjectMasterDB();
							    projectMasterList = projM.getListProject_Join_Projecthead_PCC("PCC","","",""); 
							     
				        		if (projectMasterList != null) {
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next();
			      				%>  
				      			<option value="<%=pjModel.getProject_code()%>-<%=pjModel.getProject_name()%>" ><%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%></option>
				      			
								<%		} 
									}
								%>
					   		</select>
						</div>
					</div> 
					<div class="cell colspan2"> 
			        	กำหนดเปอร์เซนต์
			        	 <div class="input-control text full-size">  
			        	 	<input id="budget" name="budget" type="number" step="0.01" dir="rtl" >
	                     </div> 
					</div>  
					<%if(freeze.equals("N")){%>
					<div class="cell colspan4"><br>
						  <button class="button success" type="submit" name="add">เพิ่มประมาณการรายได้</button> 
					</div>
					<%} %>
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
				<div class="example" data-text="รายได้">
					<%
					  	ProjectData pjdata = new ProjectData();
					  	List projectDTListreceive = pjdata.GetProjectDTDetailList(projectcode, year, "", "", "", "",
					  			"", "", "", "", "", "asc", "true", "");
					  	double pjdt_receivetotal = 0;
					  	if(projectDTListreceive != null){
					  		Iterator projectDTIter = projectDTListreceive.iterator();
					  		while(projectDTIter.hasNext()){
					  			ProjectModel pjmodel = (ProjectModel) projectDTIter.next();
					  			pjdt_receivetotal += Float.parseFloat(pjmodel.getBudget());
					  %>
					  			<!-- ROW -->
								  <div class="row cells12 " >			  
								  	<h5 class="cell colspan7 costname"><%=pjmodel.getGcostcode_name().trim()%></h5>
								  	<span class="costcodehd"><input type="hidden" id="gcostcodehd" name="gcostcodehd" value="<%=pjmodel.getGcostcode()%>"></span> 
								  	<div class="cell colspan4 align-center budget">{{<%=pjmodel.getBudget()%> | currency:"฿"}}</div>
								  	<div class="cell">
							  			<a href=""><span class="mif-cross deletebt"></span></a> 
							  		</div>
								  </div>
								<!-- ROW --> 
					  <%	
					  		}
					  	}
					  %> 
					<!-- ROW -->  
					  <!--Totle ROW -->  
					   <div class="row cells12 " >			  
					  	<div class="cell colspan7 align-right">
					  		<h4>รวม</h4>
					  	</div>
					  	<div class="cell colspan4 align-center">
					  		<h4>{{<%=pjdt_receivetotal%> | currency:"฿"}}</h4> 
					  	</div>
					  </div>
					  <!--Totle ROW -->
					  <!--Totle ROW Target -->  
					   <div class="row cells12 " >			  
					  	<div class="cell colspan7 align-right">
					  		<h4>เป้าหมาย</h4>
					  	</div>
					  	<div class="cell colspan4 align-center">
					  		<h4>{{<%=target%> | currency:"฿"}}</h4> 
					  	</div>
					  </div>
					  <!--Totle ROW Target--> 
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
		</div> 
		
		</form>
		
		<script> 
			function deleteCC() {
				$("#project_code").val(""); 
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
	   			 
	   			
	   			$('.deletebt').click(function () {  
		        	 
		        		var index = $(".deletebt").index(this);
		        	//	var cc = $(".costcodehd > #gcostcodehd").eq(index).val();
		        	//	var cc = $("#gcostcodehd").eq(index).val();
		        	//	alert(cc); 
		        		$("#project_codehd").val($(".costcodehd > #gcostcodehd").eq(index).val()); 
				    	$("#project-receivedt-pcc").submit();
		        	  
		    	});  
		    
	   			
			}); 
		</script>
		
	</body>
</html>
