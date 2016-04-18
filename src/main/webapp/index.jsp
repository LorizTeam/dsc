<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>หน้าหลัก</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.min.css"rel="stylesheet" />
		<link href="css/style.css" rel="stylesheet" />
		
		
        <script src="js/jquery-2.1.3.min.js"></script>
        <script src="js/metro.js"></script>
		<script src="js/Chart.js"></script>
        <script src="src/Chart.Line.js"></script> 
        
	</head> 

	<body >
		<%@ include file='topmenu.jsp' %>

 		<div class="container-center"> 
 			<a href="projecthd.jsp"  class="command-button bg-teal fg-white">
                <span class="icon mif-books"></span>
              	  โครงการ
			    <small>ดูรายละเอียดของแต่ละโครงการ</small>
		    </a>
		    <a href="receive-1.jsp" class="command-button bg-teal fg-white">
	    		<span class="icon mif-dollar"></span>
	    		รายได้
	    		<small>ทำรายการ รายได้ของโครงการ</small>
	    	</a>		
		    <a href="requisition.jsp" class="command-button bg-teal fg-white" >
		    	<span class="icon mif-clipboard"></span>
            	เบิกงบประมาณ
            	 <small>ทำรายการ การเบิกงบประมาณ</small>
		    </a>		
	    	
	    	<a class="command-button bg-teal fg-white">
	    		<span class="icon mif-chart-line"></span>
	    		รายงานสรุป
	    		<small>ดูรายงานสรุปของแต่ละโครงการ</small>
	    	</a>
 		</div>
		<div class="container-center">
			<div class="grid">
				<div class="row">
					<div class="cell" id="chart-area">	
																			
						
					<%@ include file='chart/bar.jsp' %>
					<%@ include file='chart/line.jsp' %>
					
					</div>			
				</div>
				<div class="row cells2">
					<div class="cell" id="chart-area">	
						<%@ include file='chart/pie.jsp' %>
					</div>
					<div class="cell" id="chart-area">	
						<%@ include file='chart/polar.jsp' %>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
