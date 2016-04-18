<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%> 
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>หน้าออกรายงาน</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/bootstrap-datepicker3.css" rel="stylesheet"> 
	
	 	<script src="js/jquery-2.1.3.min.js"></script> 
	    <script src="js/metro.js"></script>
  		<script src="js/bootstrap-datepicker-th.js"></script>
  		
  		
	</head>

	<body>
	<%
			ProjectMasterDB projM = new ProjectMasterDB();
			List projectMasterList = projM.GetProjectMasterList("", "");
		
  	%>
	
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">หน้าออกรายงาน</h3>
		 <div class="example" data-text="ช่วงวัน">
         <div class="grid">
		  	<div class="row cells4">
		        <div class="cell"> 
		        	รหัส-ชื่อ โครงการ
			        <div class="input-control text full-size">
					    <select onchange="">
					    	<%
			        		if (projectMasterList != null) {
				        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
				        			ProjectMasterForm pjInfo = (ProjectMasterForm) iterPj.next();
		      				%>  
				      			<option value="<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>" >
				       			 	<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>
				       			</option>
								<%		} 
									}
								%> 
					    </select>
					</div>
				</div> 
		        <div class="cell"> 
		        	วันที่โครงการ
		        	 <div class="input-daterange " id="daterange">
					    <input type="text" id="start" name="start" size="10" />
					    <span class="input-group-addon"> - </span>
					    <input type="text" id="endstart" name="endstart" size="10" />
					</div>
				</div> 
				<div class="cell"><br>
					  <button class="button success">Print</button> 
				</div> 
		    </div>
		 </div>  
		</div>  
		<div class="example" data-text="รายวัน">
         <div class="grid">
		  	<div class="row cells4">
		        <div class="cell"> 
		        	รหัส-ชื่อ โครงการ
			        <div class="input-control text full-size">
					    <select onchange="">
					    	<%
			        		if (projectMasterList != null) {
				        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
				        			ProjectMasterForm pjInfo = (ProjectMasterForm) iterPj.next();
		      				%>  
				      			<option value="<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>" >
				       			 	<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>
				       			</option>
								<%		} 
									}
								%> 
					    </select>
					</div>
				</div> 
		        <div class="cell"> 
		        	วันที่โครงการ
		        	<div class="input-control text full-size">
		        	 <input type="text" id="date">
		        	 </div>
				</div> 
				<div class="cell"><br>
					  <button class="button success">Print</button> 
				</div> 
		    </div>
		 </div>  
		</div>
		<div class="example" data-text="รายเดือน">
         <div class="grid">
		  	<div class="row cells4">
		        <div class="cell"> 
		        	รหัส-ชื่อ โครงการ
			        <div class="input-control text full-size">
					    <select onchange="">
					    	<%
			        		if (projectMasterList != null) {
				        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
				        			ProjectMasterForm pjInfo = (ProjectMasterForm) iterPj.next();
		      				%>  
				      			<option value="<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>" >
				       			 	<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>
				       			</option>
								<%		} 
									}
								%> 
					    </select>
					</div>
				</div> 
				<div class="cell">
						เดือน-ปี
					    <div class="input-control text full-size">
                           <input type="text" id="monthyear"> 
                        </div>
				</div>
				<div class="cell"><br>
					  <button class="button success">Print</button> 
				</div> 
		    </div>
		 </div>  
		</div>
		<div class="example" data-text="รายไตรมาส">
         <div class="grid">
		  	<div class="row cells4">
		        <div class="cell"> 
		        	รหัส-ชื่อ โครงการ
			        <div class="input-control text full-size">
					    <select onchange="">
					    	<%
			        		if (projectMasterList != null) {
				        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
				        			ProjectMasterForm pjInfo = (ProjectMasterForm) iterPj.next();
		      				%>  
				      			<option value="<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>" >
				       			 	<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>
				       			</option>
								<%		} 
									}
								%> 
					    </select>
					</div>
				</div>  
				<div class="cell">
						ไตรมาส
					    <div class="input-control text full-size">
                           <select onchange=""> 
					        <option>1</option>
					        <option>2</option>
					        <option>3</option> 
					        </select> 
                        </div>
				</div>
				<div class="cell"><br>
					  <button class="button success">Print</button> 
				</div> 
				<div class="cell"> </div>
		    </div>
		 </div>  
		</div>
		<div class="example" data-text="รายปี">
         <div class="grid">
		  	<div class="row cells4">
		        <div class="cell"> 
		        	รหัส-ชื่อ โครงการ
			        <div class="input-control text full-size">
					    <select onchange="">
					    	<%
			        		if (projectMasterList != null) {
				        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
				        			ProjectMasterForm pjInfo = (ProjectMasterForm) iterPj.next();
		      				%>  
				      			<option value="<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>" >
				       			 	<%=pjInfo.getProjectCode()%> - <%=pjInfo.getProjectName()%>
				       			</option>
								<%		} 
									}
								%> 
					    </select>
					</div>
				</div>  
				<div class="cell">
						ปี
					    <div class="input-control text full-size">
                            <input type="text" id="year">
                        </div>
				</div>
				<div class="cell"><br>
					  <button class="button success">Print</button> 
				</div> 
				<div class="cell"> </div>
		    </div>
		 </div>  
		</div>  
	</body>
	<script>
	    $(function(){
	        $("#datepicker").datepicker();
	        language: "th"
	   
	    
	    $("#monthyear").datepicker({
	    	format: "mm-yyyy",
	        startView: 2,
	        minViewMode: 1,
	        todayBtn: true,
	        clearBtn: true,
	        autoclose: true,
	        todayHighlight: true
	    });
	    $("#year").datepicker({
		    format: "yyyy",
	        startView: 2,
	        minViewMode: 2,
	        todayBtn: true,
	        clearBtn: true,
	        autoclose: true,
	        todayHighlight: true
	    });
	    $("#date").datepicker({
	        format: "dd-mm-yyyy",
	        maxViewMode: 1,
	        todayBtn: true,
	        clearBtn: true,
	        autoclose: true,
	        todayHighlight: true
	    });
	    
	    $(".input-daterange").datepicker({
	        format: "dd-mm-yyyy",
	        startDate: "-0d",
	        maxViewMode: 1,
	        todayBtn: true,
	        clearBtn: true,
	        autoclose: true,
	        todayHighlight: true
	    });
	    });
	</script>
</html>
