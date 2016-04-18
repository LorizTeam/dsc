<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
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
    
    <title>หน้าเบิกงบประมาณ</title>
    
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
	 	<link href="css/select2.css" rel="stylesheet">
	 	<link href="css/bootstrap-datepicker3.css" rel="stylesheet">
	 	
	 	
		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
 		<script src="js/jquery.dataTables.min.js"></script> 
		<script src="js/select2.js"></script>
		<script src="js/bootstrap-datepicker-th.js"></script>
  </head>
  
  <body>
    <div><%@include file="topmenu.jsp" %></div>
	<h3 class="align-center">การเบิกงบประมาณ</h3>
	<form action="requisition1.action" method="post">
	<div class="example" data-text="เลือกโครงการ">
		<div class="grid">
		  	<div class="row cells12">
		    	<div class="cell colspan4 offset2" >
					โครงการ
				        <div class="input-control text full-size">
						    <select id="project_code" name="project_code" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ">
							   <option value="" >กรุณาเลือกโครงการ</option>
							   <%
							   	List projectMasterList1 = null;
							   	ProjectMasterDB projM = new ProjectMasterDB();
							   	projectMasterList1 = projM.getListProject_Join_Projecthead("", "","","");
							   	List projectMasterList = projectMasterList1;
				        		if (projectMasterList != null) {
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next();
			      				%>  
				      			<option value="<%=pjModel.getProject_code()%>" >
				       			 	<%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%> - ปี <%=pjModel.getYear() %>
				       			</option>
								<%		} 
									}
								%>
					   		</select>
					   		<s:hidden id="project_year" name="requisition1model.project_year"/>
						</div>
		    	</div>
		    	<div class="cell colspan2">
		    		วันที่
		    		<div class="input-control full-size"> 
					    <s:textfield id="day" name="requisition1model.day"/>
					</div>
		    	</div>
		    </div>
		</div>
		<div class="flex-grid">
		  	<div class="row flex-just-center"> 
		    	<div class="cell colspan2" align="center"><br> 
					  <a href="requisition-2.jsp"><button class="button success full-size" type="submit" name="add">เบิกงบประมาณ</button></a> 
				</div> 
		    </div>
		</div>
	</div>
	</form>
	 <!-- End of example --> 
	<script type="text/javascript">
		$(function(){
			$("#project_code").select2();
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
