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
	String filejrxml = application.getRealPath("report/rep_projectdt_pcc.jrxml");
	System.out.println(filejrxml);
	String projectcode = (String) request.getParameter("projectcode");
	String year = (String) request.getParameter("year");
	
	File reportFile = new File(filejrxml);
	if (!reportFile.exists()){
		System.out.println("File Not found");
	}else{
		System.out.println("File is found");
	}
	    	
	
	JasperReport jr= JasperCompileManager.compileReport(filejrxml);
	
	Map prm = new HashMap();
	prm.put("prmprojectcode", projectcode);
	prm.put("prmyear", year);
	
	JasperPrint jasperPrint = JasperFillManager.fillReport(jr, prm, conn);
	
	OutputStream outStream = response.getOutputStream();
	JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	
 %>

 