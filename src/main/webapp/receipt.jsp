<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
    
    <title>หน้าออกใบเสร็จ</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width; initial-scale=1.0">
	<link rel="shortcut icon" href="/favicon.ico">
	<link href="css/metro.css" rel="stylesheet">
    <link href="css/metro-icons.css" rel="stylesheet">
    <link href="css/metro-responsive.css" rel="stylesheet">
	<link href="css/metro-schemes.css" rel="stylesheet">
	<link href="css/docs.css" rel="stylesheet"> 
	
	<script src="js/jquery-2.1.3.min.js"></script>
   	<script src="js/metro.js"></script>
   	<script src="js/docs.js"></script>
   	<script src="js/prettify/run_prettify.js"></script>
   	<script src="js/ga.js"></script> 
    <script src="js/jquery.dataTables.min.js"></script> 
    <script src="includehtml.js"></script>
    
  </head>
  
  <body>
    <div><%@include file="topmenu.jsp" %></div>
  </body>
</html>
