<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectModel.*" %>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>แก้ไขข้อมูลส่วนตัว</title>
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
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">ข้อมูลส่วนตัว</h3>
		  <form action="profile.action" method="post">
		 <div class="example" data-text="ข้อมูลส่วนตัว">
         <div class="grid ">
		  	<div class="row cells12 ">
		        <div class="cell colspan4 offset4"> 
			       	ผู้ใช้งาน (Username) 
			    	<div class="input-control text full-size"> 
			       		 <s:textfield name="personnelMasterModel.personnel_id" id="personnelid" required=""/> 
					</div>
		    	</div>
		    </div> 
		  	<div class="row cells12 ">
		        <div class="cell colspan4 offset4"> 
			       	รหัสผ่านเดิม 
			    	<div class="input-control password full-size"> 
			       		 <input type="password" name="password_old" />
					</div>
		    	</div> 
		    </div>
		  	<div class="row cells12 ">
		        <div class="cell colspan4 offset4"> 
			       	รหัสผ่านใหม่ 
			    	<div class="input-control password full-size"> 
			       		 <input type="password" name="password_new" />
					</div>
		    	</div>
		    </div>
		    <div class="row">		        
		        <div class="cell align-center">  
		       		 <button class="button warning" name="change">เปลี่ยนรหัสผ่าน</button> 
		    	</div>
			</div>
			
		    <div class="row cells12 "> 
		         <div class="cell colspan4 offset4"> 
			       	ชื่อ 
			    	<div class="input-control text full-size"> 
			       		 <s:textfield name="personnelMasterModel.personnel_name" id="personnelname" required=""/>
					</div>
		    	</div>
		    </div>
		    <div class="row cells12 ">
		         <div class="cell colspan4 offset4"> 
			       	นามสกุล 
			    	<div class="input-control text full-size"> 
			       		 <s:textfield name="personnelMasterModel.personnel_lastname" id="personnellastname" required=""/>
					</div>
		    	</div>
		    </div>
		    <div class="row cells12 ">
		       <div class="cell colspan4 offset4"> 
			     	  วันเกิด 
			    	<div class="input-control text full-size"> 
			       		 <s:textfield name="personnelMasterModel.dob" id="dob" required=""/>
					</div>
		    	</div> 
			</div> 
			<div class="row cells12 ">
		         <div class="cell colspan4  offset4 "> 
			       	เบอร์โทรศัพท์ 
			    	<div class="input-control text full-size"> 
			       		 <s:textfield name="personnelMasterModel.telephone" id="telephone" maxlength="10" required=""/>
					</div>
		    	</div> 
			</div>
		  	<div class="row cells12 ">
		         <div class="cell colspan4  offset4 "> 
			       	ที่อยู่ 
			    	<div class="input-control text full-size"> 
			       		 <s:textfield name="personnelMasterModel.address" id="address" required=""/>
					</div>
		    	</div> 
			</div>
		  	<div class="row cells12">
		        <div class="cell colspan4  offset4"> 
			       	ตำแหน่งงาน 
			    	<div class="input-control text full-size">  
			    		 <% String position = (String)request.getAttribute("position"); %>
			       		 <select id="position" name="position" required="">
							    	<option value="">โปรดเลือก</option>
							    	<option <%if(position.equals("01")) {%>selected<%} %> value="01">ผู้บริหาร</option> 
							    	<option <%if(position.equals("02")) {%>selected<%} %> value="02">บัญชี</option>
							    	<option <%if(position.equals("03")) {%>selected<%} %> value="03">พนักงานทั่วไป</option>
						</select> 
					</div>
		    	</div> 
			</div>
		  	<div class="row ">		        
		        <div class="cell align-center">  
		       		 <button class="button success" name="save">บันทึก</button>
		       		 <button class="button danger" name="clear">ยกเลิก</button>
		    	</div>
			</div>
		</div>  
	 </div>
	 </form>
	 
	 <script>
        $(function(){
			 $("#dob").datepicker({
		            	format: "dd-mm-yyyy",autoclose:true,todayBtn: "linked",todayHighlight: true
			 });
        });
	 </script>
	</body>
</html>
