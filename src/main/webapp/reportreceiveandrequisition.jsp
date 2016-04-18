<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%> 
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	String username = "", project_code = "";
	
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "013";
		
		CheckAuthenPageDB capDB = new CheckAuthenPageDB();
		
		chkAuthen = capDB.getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}else{
			project_code = capDB.getProjectCode(username);
		}
	} 
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
		<link href="css/sweetalert.css" rel="stylesheet" />
		<link href="css/select2.css" rel="stylesheet"> 
	
	 	<script src="js/jquery-2.1.3.min.js"></script> 
	    <script src="js/metro.js"></script>
  		<script src="js/bootstrap-datepicker-th.js"></script>
  		<script src="js/sweetalert.min.js"></script>
  		<script src="js/select2.js"></script>
  		
	</head>

	<body>
	<%
			ProjectMasterDB projM = new ProjectMasterDB();
			List projectMasterList = projM.getListProject_Join_Projecthead(project_code, "","","");
		
  	%>
	
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">หน้าออกรายงาน รับ/จ่าย</h3>
		 <div class="example" data-text="ช่วงวัน">
         <div class="grid">
		  	<div class="row cells4">
		  		<div class="cell colspan2"> 
		        	รหัส-ชื่อ โครงการ
			        <div class="input-control text full-size">
					    <select id="projectcode" name="projectcode">
					    	<%
			        		if (projectMasterList != null) {
			        			for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
				        			ProjectModel pjModel = (ProjectModel) iterPj.next();
		      				%>  
				      			<option value="<%=pjModel.getProject_code()%>" >
				       			 	<%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%>
				       			</option>
								<%		} 
									}
								%>
					    </select>
					</div>
				</div>
		        <div class="cell colspan1"> 
		        	วันที่
		        	 <div class="input-daterange " id="daterange">
					    <input type="text" id="fromdate" name="fromdate" size="10" />
					    <span class="input-group-addon"> - </span>
					    <input type="text" id="todate" name="todate" size="10" />
					</div>
				</div>  
				<div class="cell colspan1"> <br>
					<a href="javascript:print('');" class="button warning full-size"><span class="mif-print mif-lg fg-white"></span></a>
				</div>
				<div class="cell colspan1"> </div>
		    </div>
		 </div>  
		</div>  
		
	</body>
	<script>
	function print(){
		var projectcode = document.getElementById("projectcode").value;
		var fromdate 	= document.getElementById("fromdate").value;
		var todate 		= document.getElementById("todate").value;
		
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
				var load = window.open('/pcpnru/receiveRequisitionReport.action?projectcode='+projectcode+'&fromdate='+fromdate+'&todate='+todate+' ' 
						,'scrollbars=yes,menubar=no,height=600,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
			swal("พิมพ์เอกสารสำเร็จแล้ว!", "โปรดตรวจสอบรายละเอียดของเอกสารอีกครั้งเพื่อความถูกต้อง !", "success");
			} 
			 , 1000);
 
			}else {    
			 swal("ยกเลิกการพิมพ์เอกสาร", "คุณสามารถพิมพ์เอกสารได้อีกครั้งหลังจากปิดหน้าต่างนี้ !", "error");   
			}
		});
		
		}
	
	    $(function(){
	    	$("#projectcode").select2();
	 /*   $(".input-daterange").datepicker({
	        format: "dd-mm-yyyy",
	      startDate: "-0d",
	        maxViewMode: 1,
	        todayBtn: true,
	        clearBtn: true,
	        autoclose: true,
	        todayHighlight: true
	    }); */
	    
	    $("#fromdate").datepicker({
	    	format: "dd-mm-yyyy",
	    	maxViewMode: 1,
	        todayBtn: true,
	        clearBtn: true,
	        autoclose: true,
	        todayHighlight: true
	    });
	    $("#todate").datepicker({
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
