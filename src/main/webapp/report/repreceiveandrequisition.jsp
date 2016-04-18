<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.jasperreports.engine.*" %> 
<%@ page import="java.util.*" %> 
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@page import="pcpnru.utilities.DBConnect"%> 
<%@ page contentType="application/pdf" %>
<%
	DBConnect dbcon = new DBConnect();
	Connection conn = dbcon.getConnectMYSql();
	String filejrxml = application.getRealPath("report/receiveandcharges.jrxml");
	System.out.println(filejrxml);
	String projectcode 		= (String) request.getAttribute("projectcode");
	String fromdate		 	= (String) request.getAttribute("fromdate");
	String todate		 	= (String) request.getAttribute("todate");
	
	String fromday 		= (String) request.getAttribute("fromday");
	String today		= (String) request.getAttribute("today");
	String frommonth	= (String) request.getAttribute("frommonth");
	String tomonth		= (String) request.getAttribute("tomonth");
	
	File reportFile = new File(filejrxml);
	if (!reportFile.exists()){
		System.out.println("File Not found");
	}else{
		System.out.println("File is found");
	}
	    	
	
	JasperReport jr= JasperCompileManager.compileReport(filejrxml);
	
	Map prm = new HashMap();
	prm.put("prmprojectcode", projectcode);
	prm.put("prmfromdate", fromdate);
	prm.put("prmtodate", todate);
	prm.put("fromday", fromday);
	prm.put("today", today);
	prm.put("frommonth", frommonth);
	prm.put("tomonth", tomonth);
	
	JasperPrint jasperPrint = JasperFillManager.fillReport(jr, prm, conn);
	
	OutputStream outStream = response.getOutputStream();
	JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	
 %>
